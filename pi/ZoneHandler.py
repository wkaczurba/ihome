'''
Created on Apr 1, 2017

@author: WKaczurb
'''
from timer.TimerHandler import TimerHandler
from timer.TimerSetting import TimerSetting

# TODO: Add logger, uncomment prints in ZoneHandler.gpioOutput and chnage them to output via logger.

class ZoneHandler(object):
    '''
    classdocs
    '''


    def __init__(self, zoneSetting):
        '''
        Constructor
        '''
        
        self.zoneSetting = zoneSetting
        if not isinstance(zoneSetting, dict):
            raise TypeError("zoneSetting must be of dict type")
        
        if not (zoneSetting.keys().__contains__("mode") and zoneSetting.keys().__contains__("automaticModeSettings")):
            raise ValueError("zoneSetting must contain keys 'mode' and 'automaticModeSettings'")

        self.timerHandler = None # placeholder
        self.gpioOn = lambda x: self.unAssignedGpioOn(x)
        self.gpioOff = lambda x: self.unAssignedGpioOff(x)
        self.applyZoneSettings()

    def unAssignedGpioOn(self, what):
        print "GpioOn unassigned"

    def unAssignedGpioOff(self, what):
        print "GpioOff unassigned"
        
    def setGpioOn(self, gpioOn):
        self.gpioOn = gpioOn
    
    def getGpioOn(self):
        return self.gpioOn
    
    def setGpioOff(self, gpioOff):
        self.gpioOff = gpioOff
    
    def getGpioOff(self):
        return self.gpioOff
    
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
        # TODO: unregister everything...
        # remove observers
        self.timerHandler.removeAllObservers()
        self.timerHandler.stop()
        self.timerHandler = None    
        self.removeTimerHandler()
        self.gpioOutput(False)
        """Remove timer handler, remove observers. """            
    
    # TODO: timerHandler argument is redundant in this case; remove it   
    def update(self, timerHandler):
        if (self.zoneSetting["mode"] == "MANUAL_ON"):
            self.gpioOutput(True);
        elif (self.zoneSetting["mode"] == "MANUAL_OFF"):
            self.gpioOutput(False);
        elif (self.zoneSetting["mode"] == "AUTOMATIC"):
            # depends...
            if (self.timerHandler.checkIfFits()):
                self.gpioOutput(True);
            else:
                self.gpioOutput(False);
        else:
            raise ValueError("ZoneSetting['mode'] should be within MANUAL_ON, MANUAL_OFF, AUTOMATIC")
        pass
    
    def gpioOutput(self, on):
        if (on == True):
            self.gpioOn(self)
            #print str(self) + " : Setting GPIO on"
        else:
            self.gpioOff(self)
            #print str(self) + " : Setting GPIO off"
        # There must be another objecct setting GPIOs.
        
    
    