'''
Created on Mar 7, 2017

@author: WKaczurb
'''
import logging

logging.basicConfig(format='%(asctime)s [%(name)s]: %(message)s', datefmt='%m/%d/%Y %I:%M:%S %p', level=logging.INFO)
logger = logging.getLogger('app')

from server.ServerSubscriber import ServerSubscriber
import time
from ZoneHandler import ZoneHandler
from gpios import GpioFactory

zoneHandlers = []
zoneHandlersUpdateTimestamp = None # Timestamp
zonesCreated = False
TIMEOUT = 30 
lastValidSettings = None
lastTimeoutSettings = None
status = []
settings = None # Normally => []    

def createZoneHandlers(settings):
    global zoneHandlers
    
    assert (len(zoneHandlers) == 0)
    zonesSettings = settings['zones']
    
    for i in range(0, len(zonesSettings)):
        zoneSetting = zonesSettings[i]

        # Create new:            
        gpioPin = GpioFactory.getGpioPin(i)
        logger.info("createZoneHandlers() -> creating zone " + str(i))
        zoneHandler = ZoneHandler(zoneSetting, gpioPin)
        zoneHandler.zoneId = i
        zoneHandlers.append(zoneHandler)
        
def deleteNonExistingZoneHandlers(settings): 
    global zoneHandlers
    
    assert (len(zoneHandlers) > 0)
    zonesSettings = settings['zones']
               
    for i in range(len(zonesSettings), len(zoneHandlers)):
        # Delete...
        logger.error("deleteNonExistingZoneHandlers - deleting zones; should never get here!")
        #raise AssertionError("This one should never get here!")
        print "deleting timer for zone: %d" % (i)
        zoneHandlers[i].kill()
        zoneHandlers[i] = None        
    
    zoneHandlers = zoneHandlers[0:len(zonesSettings)] # Remove from the list Nones  

updateZonesPrevSettings = None
def updateZones(settings):
    global zoneHandlers
    global updateZonesPrevSettings
    
    zonesSettings = settings['zones']    

    assert (len(zoneHandlers) > 0)
    assert (len(zonesSettings) == len(zoneHandlers)) # Suggests that zoneHanlders or its number has changed on the fly...
    
    if (updateZonesPrevSettings != settings):
        for i in range(0, len(zonesSettings)):
            # Update each zone with new zoneSettings
            zoneHandlers[i].setZoneSetting(zonesSettings[i])
        updateZonesPrevSettings = settings
        
def updateZonesWithEmpty():
    global zoneHandlers
    global updateZonesPrevSettings
    
    assert (len(zoneHandlers) > 0)
    for i in range(0, len(zoneHandlers)):
        # Update i-th zoneHandler with empty data:
        zoneHandlers[i].setZoneSetting([])
    updateZonesPrevSettings = None
        
def getGpioPinValues():
    pins = GpioFactory.getInstantiatedGpioPins()
    values = []
    for pin in pins:
        values.append(pin.getValue()) # NOT IDEAL...               
    return values

def updateTimestamp():
    global zoneHandlersUpdateTimestamp
    zoneHandlersUpdateTimestamp = time.time()

    
def isTimeout():
    global TIMEOUT
    global zoneHandlersUpdateTimestamp
    
    return (time.time() - zoneHandlersUpdateTimestamp >= TIMEOUT)


def updateZoneHandlers(settings):
    global zoneHandlers
    global zonesCreated
    global zoneHandlersUpdateTimestamp
    global lastValidSettings
    global lastTimeoutSettings
    
    # if settings contains no zones -> 
    #    -> it means no data is available;
    
    if (isinstance(settings, dict.__class__)):
        raise TypeError("settings must be of dict type")
        
    zonesSettingsValid = False
    if (settings=={}):
        zonesSettingsValid = False
    else:
        zonesSettingsValid = True
        lastValidSettings = settings
        
    # TODO: Remove the one below with a state machine:
    if (not zonesCreated):
        if (zonesSettingsValid):
            createZoneHandlers(settings)
            zonesCreated = True
            updateTimestamp()
    else:
        if (zonesSettingsValid):
            updateZones(settings)
            updateTimestamp()            
        else: # (NO DATA).
            if (not isTimeout()):
                
                # TODO: Send lastValidSettings only once or even 0.
                if (lastTimeoutSettings != lastValidSettings):
                    logger.warn("NO DATA -> USING PREVIOUS SETTINGS (zoneHandlersUpdateTimestamp=%d ; time.time()=%d)" % (zoneHandlersUpdateTimestamp, time.time()))
                    updateZones(lastValidSettings)
                lastTimeoutSettings = lastValidSettings
            else: # 
                if (lastTimeoutSettings != []):
                    logger.warn("NO DATA TIMEOUT -> SENDING EMPTY SETTINGS (zoneHandlersUpdateTimestamp=%d ; time.time()=%d; time.time() - zoneHandlersUpdateTimestamp=%d)" % (zoneHandlersUpdateTimestamp, time.time(), time.time() - zoneHandlersUpdateTimestamp))
                    updateZonesWithEmpty()
                    lastTimeoutSettings = []
    
    return getGpioPinValues()
    
# def settingsChangedOrEmpty():
#     global hs
#     global settings
#     global status
#     
#     #print "Settings change detected"
# 
#     newSettings = hs.getSettings()
#     if (newSettings != settings):
#         if (settings == None):
#             logger.info("First settings received")
#         else:
#             logger.info("Settings change detected.")
#     status = updateZoneHandlers(newSettings)
#     putStatusAndSettings()
#     
#     settings = newSettings
    
def putStatusAndSettings():
    global settings
    global status
    
    logger.debug("putting statuses: " + str(status))    
    hs.putStatusREST(status)
    hs.putSettingsREST(settings)    
    
if __name__ == '__main__':
    logger.info("running")
    hs = ServerSubscriber(device=0)
    
    # HERE:
    newSettings = hs.getSettingsREST()
    status = updateZoneHandlers(newSettings)  
    settings = newSettings
    putStatusAndSettings()
    
    while (True):
        time.sleep(1)
        newSettings = hs.getSettingsREST()

        if (newSettings != settings):
            logger.info("New settings receied: %s vs %s" % (newSettings, settings)) # FIXME; Here is a problme.
        if (newSettings == {} and settings != {}):
            logger.info("Lost connectivity (empty settings)")
        if (newSettings != {} and settings == {}):
            logger.info("Connectivity restored")
            
        status = updateZoneHandlers(newSettings)          
        settings = newSettings
        putStatusAndSettings() # Update every so often about the reads.
        
    