'''
Created on Mar 7, 2017

@author: WKaczurb
'''
import logging

logging.basicConfig(format='%(asctime)s [%(name)s]: %(message)s', datefmt='%m/%d/%Y %I:%M:%S %p', level=logging.INFO)
logger = logging.getLogger('app')

from server.HeatingService import HeatingService
from timer.TimerSetting import TimerSetting
import time
import pprint
import datetime
from timer.TimerHandler import TimerHandler
from ZoneHandler import ZoneHandler
from gpios import GpioFactory

zoneHandlers = []

def updateZoneHandlers(settings):
    global zoneHandlers
    
    zonesSettings = settings['zones']
    for i in range(0, len(zonesSettings)):
        zoneSetting = zonesSettings[i]
        
        # Get:
        if (i < len(zoneHandlers)):
            # Get existing timers:
            zoneHandler = zoneHandlers[i]
            
            # Update / set.
            zoneHandler.setZoneSetting(zoneSetting)
        elif (i >= len(zoneHandlers)):
            # Create new:            
            gpioPin = GpioFactory.getGpioPin(i)
            logger.info("updateZoneHandlers() -> creating zone " + str(i))
            zoneHandler = ZoneHandler(zoneSetting, gpioPin)
            zoneHandler.zoneId = i
            zoneHandlers.append(zoneHandler)
        else:
            assert (False, "This state should have never been reached.")
            
        # Update / set.
        #zoneHandler.setZoneSetting(zoneSetting)
                          
    for i in range(len(zonesSettings), len(zoneHandlers)):
        # Delete...
        logger.error("updateZoneHandlers - deleting zones; should never get here!")
        #raise AssertionError("This one should never get here!")
        print "deleting timer for zone: %d" % (i)
        zoneHandlers[i].kill()
        zoneHandlers[i] = None
        
    # Remove from the list Nones
    zoneHandlers = zoneHandlers[0:len(zonesSettings)]

    # FIXME: The code below does not ensure that pins are created in a consecutive order.
    # which means that they are output in the order of creation; is it good? 
    pins = GpioFactory.getInstantiatedGpioPins()
    values = []
    for pin in pins:
        values.append(pin.getValue()) # NOT IDEAL...   
    return values
    
    
def settingsChanged():
    global hs
    #print "Settings change detected"
    logger.info("Settings change detected.")

    settings = hs.getTimerSettings()
    status = updateZoneHandlers(settings)
    
    #updateZoneHandlers(settings)
    hs.putStatusREST(status)
    hs.putSettingsREST(settings)    
    

if __name__ == '__main__':
    logger.info("running")
    hs = HeatingService(1)
    hs.addSettingsChangeObserver(lambda: settingsChanged())
    
    settings = hs.getSettingsREST()
    updateZoneHandlers(settings)
    
    while (True):
        time.sleep(1)
        hs.getSettingsREST() # TODO: This should be getSettingsREST.
    