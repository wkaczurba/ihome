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
#from sets import Set

# TODO: Rework it so it is a class...

gpios = [] # settings of gpios
timerHandlers = []

def setRelays(relays):
    print "TODO Function -> set relays"
    
#def tmpChange(zoneId, timerHandler):
def tmpChange(timerHandler):
    print "tmpChange : zone_id == %d; timerHandler=%s" % (timerHandler.zoneId, timerHandler.__str__())    
    
def applyTimerHandlers(settings):
    """ Set timer for each of zones...!"""
    global timerHandlers
    
    zones = settings['zones']

#     if (len(timerHandlers) != len(zones)):
#         # UPDATE IF NEEDED:     
#         if (len(timerHandlers) < len(zones)):
#             timerHandlers += [None,] * (len(zones) - len(timerHandlers))
#         elif (len(timerHandlers) < len(zones)):
#             # DELETE/SHUT ALL IRRELEVANT (NON-EXISTING) TIMERS.
#             raise NotImplementedError("Zones deleted - not implemented") 

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
            
            
            #FIXME: There is a mismatch:  tmpChange always says that last timerHandler from timerHandlers calls tmpChange.
            #Change timerHandler so it prints all TimerSettings
            #Make sure all TimerSettings are properly set
            #Then make sure that tmpChange lambda is correct, and params provided are not constant [what appears to be the case]
            #@If the problem is with lambda -> Possible solution: -> pass zone id throguh timerHandler.
    
                
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
            #if (not zone['manualModeSetting'] in (True, False)):
            #    raise ValueError("Zone[%d].manualModeSetting is neither True or False. It is: %s" % (i, str(zone['manualModeSetting'])))
            #gpios[i] = zone['manualModeSetting']
            assert(len(gpios) == i)
            #gpios.append(zone['manualModeSetting'])
            gpios.append(True)
            #timerHandlers.append(None)
            
        elif (zone['mode'] == "MANUAL_OFF"):
            gpios.append(False)
            #timerHandlers.append(None)
            
        elif (zone['mode'] == "AUTOMATIC"):
            #now = datetime.datetime.now()
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
    setRelays(gpios)
    status = gpios
    return gpios

def settingsChanged():
    global hs
    print "Settings change detected"

    settings = hs.getTimerSettings()
    status = applySettings(settings)

    hs.putStatusREST(status)
    hs.putSettingsREST(settings)    
    

if __name__ == '__main__':
    print "running"
    hs = HeatingService(1)
    hs.addSettingsChangeObserver(lambda: settingsChanged())
    
    settings = hs.getSettingsREST()
    applySettings(settings)
    
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