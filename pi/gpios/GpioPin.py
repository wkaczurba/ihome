import logging
#import traceback
import sys
'''
Created on Apr 1, 2017

@author: WKaczurb
'''

class GpioPin(object):
    '''
    classdocs
    '''

    def __init__(self, pinNumber):
        '''
        Constructor
        '''
        #logging.basicConfig(format='%(asctime)s [%(name)s]: %(message)s', datefmt='%m/%d/%Y %I:%M:%S %p')
        self.logger = logging.getLogger('GpioPin')
        self.pinNumber = pinNumber
        self.output = False
        self.outputValue = 0
        # TODO: Register that GpioPin exists? and is registered only once?
        
    def getPinNumber(self):
        return self.pinNumber
        
    def gpioOutputMode(self):
        self.output = True
        # TODO: gpioOutputMode needs to be properly implemented
    
    def gpioInputMode(self):
        self.output = False
        # TODO: gpioInputMode needs to be properly implemented
        
    def gpioOutputHigh(self):
        self.gpioOutputMode()
        self.outputValue = 1
        self.logger.info ("Setting GPIO (%d) == 1" % (self.pinNumber))
        #traceback.print_stack(file=sys.stdout)
        # TODO: gpioOuputHigh needs to be properly implemented
    
    def gpioOutputLow(self):
        self.gpioOutputMode()
        self.outputValue = 0
        self.logger.info ("Setting GPIO (%d) == 0" % (self.pinNumber))
        #traceback.print_stack(file=sys.stdout)
        # TODO: gpioOuputLow needs to be properly implemented
    
    def gpioInput(self):
        self.gpioInputMode()
        # TODO: gpioInput needs to be properly implemented
        
    def isOutput(self):
        return self.output
    
    def readInputValue(self):
        raise NotImplementedError("read Input value not implemented yet")
        
    def getValue(self):
        if (self.output):
            return self.outputValue
        else:
            return self.readInputValue()
    