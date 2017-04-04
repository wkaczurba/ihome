'''
Created on Apr 1, 2017

@author: WKaczurb
'''
from timer.TimerHandler import TimerHandler
from timer.TimerSetting import TimerSetting
from gpios.GpioPin import GpioPin

# TODO: Add logger, uncomment prints in ZoneHandler.gpioOutput and chnage them to output via logger.

class ZoneHandler(object):
    '''
    classdocs
    '''

    def __init__(self, zoneSetting, gpioPin):
        '''
        Constructor
        '''
        
        self.zoneSetting = zoneSetting
        if not isinstance(zoneSetting, dict):
            raise TypeError("zoneSetting must be of dict type")
        
        if not (zoneSetting.keys().__contains__("mode") and zoneSetting.keys().__contains__("automaticModeSettings")):
            raise ValueError("zoneSetting must contain keys 'mode' and 'automaticModeSettings'")
        
        if not isinstance(gpioPin, GpioPin):
            raise TypeError("gpioPin must be of type GpioPin (from gpios.GpioPin module)")

        self.timerHandler = None # placeholder
        self.gpioPin = gpioPin # placeholder for class that handles Gpio's Pin.
        self.applyZoneSettings()

    def setGpioPin(self, gpioPin):
        self.gpioPin = gpioPin
        
    def getGpioPin(self):
        return self.gpioPin
    
    def applyZoneSettings(self):
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
        self.zoneSetting = zoneSetting
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
        
    
