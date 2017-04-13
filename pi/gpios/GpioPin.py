import logging
#import traceback
import sys
'''
Created on Apr 1, 2017

@author: WKaczurb
'''

import platform

if ("raspberrypi" in platform.uname()):
    import RPi.GPIO as GPIO
else:
    import RPiDummy.GPIO as GPIO

class GpioPin(object):
    '''
    classdocs
    '''

    def __init__(self, pinNumber):
        '''
        Constructor
        '''
        self.logger = logging.getLogger('GpioPin')
        
        if (pinNumber == 0):
            self.physicalPiPin = 2 
        elif (pinNumber == 1):
            self.physicalPiPin = 3
        elif (pinNumber == 2):
            self.physicalPiPin = 4
        else:
            raise "pi pin other than 0, 1, 2. - unhandled."
        

        self.pinNumber = pinNumber
        #self.piBcmNumber = 
        self.output = False
        self.outputValue = 0
        # TODO: Register that GpioPin exists? and is registered only once?        
        
    def getPinNumber(self):
        return self.pinNumber
        
    def gpioOutputMode(self):
        GPIO.setup(self.physicalPiPin, GPIO.OUT)
        self.output = True
        # TODO: gpioOutputMode needs to be properly implemented
    
    def gpioInputMode(self):
        GPIO.setup(self.physicalPiPin, GPIO.IN)
        self.output = False
        # TODO: gpioInputMode needs to be properly implemented
        
    def gpioOutputHigh(self):
        self.gpioOutputMode()
        self.outputValue = 1
        GPIO.output(self.physicalPiPin, GPIO.HIGH)
        self.logger.info ("Setting GPIO (logic=%d, piGpio=%d) == 1" % (self.pinNumber, self.physicalPiPin))
        #traceback.print_stack(file=sys.stdout)
        # TODO: gpioOuputHigh needs to be properly implemented
    
    def gpioOutputLow(self):
        self.gpioOutputMode()
        self.outputValue = 0
        GPIO.output(self.physicalPiPin, GPIO.LOW)
        self.logger.info ("Setting GPIO (logic=%d, piGpio=%d) == 0" % (self.pinNumber, self.physicalPiPin))
        #traceback.print_stack(file=sys.stdout)
        # TODO: gpioOuputLow needs to be properly implemented
    
    def gpioInput(self):
        self.logger.warn ("gpioInput(self): not implemented yet; ignoring")
        return 
        self.gpioInputMode() # (IS THIS NEEDED?)
        return GPIO.input(self.physicalPiPin)
        
    def isOutput(self):
        self.logger.warn ("isOutput(self): not implemented yet; ignoring")
        return
        #return self.output
    
    def readInputValue(self):
        raise NotImplementedError("read Input value not implemented yet")
        
    def getValue(self):
        if (self.output):
            return self.outputValue
        else:
            return self.readInputValue()

    