'''
Created on Apr 1, 2017

@author: WKaczurb
'''
import time
import logging
from timer.TimerHandler import TimerHandler
from timer.TimerSetting import TimerSetting
from gpios.GpioPin import GpioPin
#from nt import abort

# TODO: Add logger, uncomment prints in ZoneHandler.gpioOutput and chnage them to output via logger.

class ZoneHandler(object):
    '''
    classdocs
    '''

    def __init__(self, zoneSetting, gpioPin):
        '''
        Constructor
        '''
        #self.noDataTimeoutTime = 30 # 30 seconds acceptable when no data is received.  
        self.logger = logging.getLogger('ZoneHandler')
        
        if (not ZoneHandler.isValidZoneSettings(zoneSetting)): 
            self.zoneSetting = None
            raise ValueError("zoneSetting must contain keys 'mode' and 'automaticModeSettings'")
        else:
            self.zoneSettingTimestamp = time.time() # timestamp when last time setting was received.
            self.zoneSetting = zoneSetting
                
        if not isinstance(gpioPin, GpioPin):
            raise TypeError("gpioPin must be of type GpioPin (from gpios.GpioPin module)")
        
        self.timerHandler = None # placeholder
        self.gpioPin = gpioPin # placeholder for class that handles Gpio's Pin.
        self.applyZoneSettings()

    def setGpioPin(self, gpioPin):
        self.gpioPin = gpioPin
        
    def getGpioPin(self):
        return self.gpioPin

    # TODO: This funciton should be moved to a seprate class ZoneSetting or similar.
    @staticmethod
    def isValidZoneSettings(z):
        if not isinstance(z, dict):
            return False 
        
        if not (z.keys().__contains__("mode") and z.keys().__contains__("automaticModeSettings")):
            raise ValueError("zoneSetting must contain keys 'mode' and 'automaticModeSettings'")
        
            
        if not (z.keys().__contains__("mode") and z.keys().__contains__("automaticModeSettings")):
            return False
            #raise ValueError("zoneSetting must contain keys 'mode' and 'automaticModeSettings'")
        return True
        
    def applyZoneSettings(self):
        if (self.zoneSetting == None):
            assert (self.timerHandler != None) # timerHandler should be defined here.
            self.timerHandler.notifyObservers()
            return
            
        timerSettings = TimerSetting.createTimerSettings(self.zoneSetting['automaticModeSettings'])
        if (self.timerHandler == None):
            self.timerHandler = TimerHandler()
            self.timerHandler.setTimerSettings(timerSettings)
        
            self.timerHandler.addObserver(lambda x: self.update(x)) # x => timerHandler
            self.timerHandler.start()
        else:
            self.timerHandler.setTimerSettings(timerSettings)
        self.timerHandler.notifyObservers()
        
    def getTimerHandler(self):
        return self.timerHandler
    
    def getZoneSetting(self):
        return self.zoneSetting
    
    def setZoneSetting(self, zoneSetting):
        if (ZoneHandler.isValidZoneSettings(zoneSetting)):
            self.zoneSetting = zoneSetting
            self.zoneSettingTimestamp = time.time()
        else:
            self.zoneSetting = None
            
        self.applyZoneSettings()
    
    def kill(self):
        """Remove timer handler, remove observers. """            
        # unregister everything... and remove observers
        self.timerHandler.removeAllObservers()
        self.timerHandler.stop()
        self.timerHandler = None    
        self.removeTimerHandler()
        self.gpioOutput(False)
    
    # TODO: timerHandler argument is redundant in this case; remove it   
    def update(self, timerHandler):       
        if (self.zoneSetting == None):
            self.logger.warn("No valid data received - switching off zone.")
            self.gpioOutput(False)
            return
        
        if (self.zoneSetting["mode"] == "MANUAL_ON"):
            self.gpioOutput(True)
        elif (self.zoneSetting["mode"] == "MANUAL_OFF"):
            self.gpioOutput(False)
        elif (self.zoneSetting["mode"] == "AUTOMATIC"):
            # in the automatic mode - output depends on time:
            if (self.timerHandler.checkIfFits()):
                self.gpioOutput(True)
            else:
                self.gpioOutput(False)
        else:
            raise ValueError("ZoneSetting['mode'] should be within MANUAL_ON, MANUAL_OFF, AUTOMATIC")
        pass
    
    def gpioOutput(self, on):
        if (on == True):
            self.gpioPin.gpioOutputHigh()
        else:
            self.gpioPin.gpioOutputLow()
        
    
