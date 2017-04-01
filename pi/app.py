'''
Created on Mar 7, 2017

@author: WKaczurb
'''
from server.HeatingService import HeatingService
from timer.TimerSetting import TimerSetting
import time
import pprint
import datetime
from timer.TimerHandler import TimerHandler
from ZoneHandler import ZoneHandler

# TODO: Proper GPIO class/object instead of Lambdas.

gpios = [] # settings of gpios
timerHandlers = []

#def tmpChange(zoneId, timerHandler):
def tmpChange(timerHandler):
    print "tmpChange : zone_id == %d; timerHandler=%s" % (timerHandler.zoneId, timerHandler.__str__())    
    
def applyTimerHandlers(settings):
    """ Set timer for each of zones...!"""
    global timerHandlers
    
    zones = settings['zones']

    for i in range(0, len(zones)):
        zone = zones[i]
        
        # DOES IT EXIST?
        #  IS THE SETTINGS CORRECT?
        #  ?
        
        timerSettings = []
        timerSettings = TimerSetting.createTimerSettings(zone['automaticModeSettings'])
        
        if (i < len(timerHandlers)):
            # Update existing timers:
            timerHandler = timerHandlers[i]
            
            # TODO: Check if there is change in timerSettings and report if there is.
            timerHandler.setTimerSettings(timerSettings) 
        elif (i >= len(timerHandlers)):
            # Create new: 
            
            timerHandler = TimerHandler()
            timerHandler.zoneId = i
            timerHandler.setTimerSettings(timerSettings)
            #TODO: timerHandler.addObserver(lambda: print("timer %d changed to %d" % (i, timerHandler.isOn())))
            # THIS REQUIRES FIXING:
            timerHandler.addObserver(lambda x: tmpChange(x))            
            #TODO: timerHandler.notifyObservers()
            timerHandler.start()
            print "appending new timer i=%d, %s to %s" % (i, timerHandler, timerHandlers)
            timerHandlers.append(timerHandler)
              
    for i in range(len(zones), len(timerHandlers)):
        raise AssertionError("This one should never get here!")
        print "deleting timer for zone: %d" % (i)
        # Iteration through non-existing zones:
        timerHandlers[i].removeAllObservers()
        timerHandlers[i].stop()
        timerHandlers[i] = None
        
    # Remove from the list Nones
    timerHandlers = timerHandlers[0:len(zones)]
        
    print "timerHandlers: " + str(timerHandlers)
        #TODO: Update all TimerHandler
  
def applySettings(settings):
    global gpios, timerHandlers
    print "Apllying settings: " + pprint.pformat(settings)
    
    applyTimerHandlers(settings)
    
    # TODO: It should calcalate what to turn on and what to turn off...
    zones = settings['zones']
    gpios = []
    
    # TODO: Resize array if needed; each where Automatic Mode is set -> should have its own Timer.
    #timerHandlers = [] # Need to close previous settings!. Each zone should have one timerHandler.
    
    for i in range(0, len(zones)):
        zone = zones[i]
        if (zone['mode'] == "MANUAL_ON"):
            assert(len(gpios) == i)
            gpios.append(True)
        elif (zone['mode'] == "MANUAL_OFF"):
            gpios.append(False)
        elif (zone['mode'] == "AUTOMATIC"):
            settings = []
            for automaticModeSettings in zone['automaticModeSettings']:
                days = automaticModeSettings['days']
                months = automaticModeSettings['months']
                startingTime = automaticModeSettings['startingTime']
                endTime = automaticModeSettings['endTime']
                setting = TimerSetting(days, months, startingTime, endTime)
                settings.append(setting)
            pass
        else:
            raise ValueError("Zone[%d].mode is not within: MANUAL_ON, MANUAL_OFF or AUTOMATIC. It is: %s" % (i, str(zones)))
    
    print "GPIOs: " + str(gpios)
    #status = [True, True, True]
    status = gpios
    return gpios

zoneHandlers = []

# TODO: Modify GPIOs so change of their status is automatically send as a status
gpios = []

def gpioOn(zoneHandler):
    global gpios
    #print "zoneHandler: " + str(zoneHandler) + " turns gpioOn (zoneId=%d)" % (zoneHandler.zoneId)
    print "turns gpioOn (zoneId=%d)" % (zoneHandler.zoneId)
    #print "len(gpios)=%d" % (len(gpios))
    gpios[zoneHandler.zoneId] = 1;
    # TODO: The change of GPIO should trigger status update

def gpioOff(zoneHandler):
    global gpios
    #print "zoneHandler: " + str(zoneHandler) + " turns gpioOff (zoneId=%d)" % (zoneHandler.zoneId)
    print "turns gpioOff (zoneId=%d)" % (zoneHandler.zoneId)
    #print "len(gpios)=%d" % (len(gpios))
    gpios[zoneHandler.zoneId] = 1;
    # TODO: The change of GPIO should trigger status update

def updateZoneHandlers(settings):
    global zoneHandlers, gpios
    
    zonesSettings = settings['zones']
    #gpios = [0,] * len(zonesSettings) # initialize with zeros
    for i in range(0, len(zonesSettings)):
        zoneSetting = zonesSettings[i]
        
        if (i >= len(gpios)):
            gpios.append(0) # it will be set automatically later
                
        # Get:
        if (i < len(zoneHandlers)):
            # Get existing timers:
            zoneHandler = zoneHandlers[i]
        elif (i >= len(timerHandlers)):
            # Create new:            
            zoneHandler = ZoneHandler(zoneSetting)
            zoneHandler.zoneId = i
            zoneHandler.setGpioOn(lambda zoneHndlr: gpioOn(zoneHndlr))
            zoneHandler.setGpioOff(lambda zoneHndlr: gpioOff(zoneHndlr))
            
        # Update / set.
        zoneHandler.setZoneSetting(zoneSetting)
                          
    for i in range(len(zonesSettings), len(zoneHandlers)):
        # Delete...
        
        raise AssertionError("This one should never get here!")
        print "deleting timer for zone: %d" % (i)
        zoneHandlers[i].kill()
        zoneHandlers[i] = None
        
    # Remove from the list Nones
    gpios = gpios[0:len(zonesSettings)]
    zoneHandlers = zoneHandlers[0:len(zonesSettings)]
    
    return gpios
    
    
def settingsChanged():
    global hs
    print "Settings change detected"

    settings = hs.getTimerSettings()
    #status = applySettings(settings)
    #status = applySettings2(settings)
    status = updateZoneHandlers(settings)
    
    updateZoneHandlers(settings)

    hs.putStatusREST(status)
    hs.putSettingsREST(settings)    
    

if __name__ == '__main__':
    print "running"
    hs = HeatingService(1)
    hs.addSettingsChangeObserver(lambda: settingsChanged())
    
    settings = hs.getSettingsREST()
    #applySettings(settings)
    updateZoneHandlers(settings)
    
    while (True):
        time.sleep(1)
        hs.getSettingsREST() # TODO: This should be getSettingsREST.
    
    #pprint.pprint(settings)
    #print settings
    
# x = 0;
# while (x < 1):
#     h.getSettingsREST()
#     #print h.settings
#     print "applying settings"
#     h.putStatus([True, False, False])
#     h.putSettings(h.settings)
#     x+=    