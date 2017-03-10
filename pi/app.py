'''
Created on Mar 7, 2017

@author: WKaczurb
'''
from server.HeatingService import HeatingService
import timer
import time
import pprint
import datetime

# TODO: Rework it so it is a class...

def setRelays(relays):
    print "TODO Function -> set relays"

def applySettings(settings):
    print "Apllying settings: " + pprint.pformat(settings)
    
    # TODO: It should calcalate what to turn on and what to turn off...
    zones = settings['zones']
    gpios = [] # settings of gpios
    for i in range(0, len(zones)):
        zone = zones[i]
        if (zone['mode'] == "MANUAL"):
            if (not zone['manualModeSetting'] in (True, False)):
                raise ValueError("Zone[%d].manualModeSetting is neither True or False. It is: %s" % (i, str(zone['manualModeSetting'])))
            #gpios[i] = zone['manualModeSetting']
            assert(len(gpios) == i)
            gpios.append(zone['manualModeSetting'])
        elif (zone['mode'] == "AUTOMATIC"):
            now = datetime.datetime.now()
            for automaticModeSettings in zone['automaticModeSettings']:
                days = automaticModeSettings['days']
                months = automaticModeSettings['months']
                startingTime = automaticModeSettings['startingTime']
                endTime = automaticModeSettings['endTime']
                setting = timer.TimerSetting()
                # zzzz
            print "TODO: Check all "
            pass
        else:
            raise ValueError("Zone[%d].mode is neither MANUAL or AUTOMATIC. It is: %s" % (i, str(zones)))
    
    print "GPIOs: " + str(gpios)
    #status = [True, True, True]
    setRelays(gpios)
    status = gpios
    return gpios

def settingsChanged():
    global hs
    print "Settings change detected"

    settings = hs.getSettings()
    status = applySettings(settings)

    hs.putStatusREST(status)
    hs.putSettingsREST(settings)    
    

if __name__ == '__main__':
    print "running"
    hs = HeatingService()
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