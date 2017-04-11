'''
Created on Mar 7, 2017

@author: WKaczurb
'''
import logging

logging.basicConfig(format='%(asctime)s [%(name)s]: %(message)s', datefmt='%m/%d/%Y %I:%M:%S %p', level=logging.INFO)
logger = logging.getLogger('app')

from server.ServerSubscriber import ServerSubscriber
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
            raise AssertionError("This state should have never been reached.")
            
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
    
status = []
settings = []    

def settingsChanged():
    global hs
    global settings
    global status
    
    #print "Settings change detected"
    logger.info("Settings change detected.")

    settings = hs.getSettings()
    status = updateZoneHandlers(settings)
    #putStatusAndSettings(status, settings)
    putStatusAndSettings()
    
    
#def putStatusAndSettings(status, settings):
def putStatusAndSettings():
    global settings
    global status
    
    logger.debug("putting statuses: " + str(status))    
    #updateZoneHandlers(settings)
    hs.putStatusREST(status)
    hs.putSettingsREST(settings)    
    
# TODO: Create updater.

if __name__ == '__main__':
    logger.info("running")
    hs = ServerSubscriber(device=0)
    hs.addSettingsChangeObserver(lambda: settingsChanged())
    
    # HERE:
    settings = hs.getSettingsAndNotifyREST()
    status = updateZoneHandlers(settings)  
    putStatusAndSettings()
    
    while (True):
        time.sleep(1)
        # HERE: Partial
        hs.getSettingsAndNotifyREST() # TODO: This should be getSettingsREST.
        putStatusAndSettings() # Update every so often about the reads.
    