import logging
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
        logging.basicConfig(format='%(asctime)s [%(name)s]: %(message)s', datefmt='%m/%d/%Y %I:%M:%S %p')
        self.logger = logging.getLogger('GpioPin')
        self.pinNumber = pinNumber
        # TODO: Register that GpioPin exists? and is registered only once?
        
    def getPinNumber(self):
        return self.pinNumber
        
    def gpioOutputMode(self):
        self.logger.warn ("gpioOutputMode needs to be properly implemented")
    
    def gpioInputMode(self):
        self.logger.warn ("gpioInputMode needs to be properly implemented")
        
    def gpioOutputHigh(self):
        self.gpioOutputMode()
        self.logger.info ("Setting GPIO (%d) == 1" % (self.pinNumber))
        self.logger.warn ("gpioOuputHigh needs to be properly implemented")
    
    def gpioOutputLow(self):
        self.gpioOutputMode()
        self.logger.info ("Setting GPIO (%d) == 0" % (self.pinNumber))
        self.logger.warn ("gpioOuputLow needs to be properly implemented")
    
    def gpioInput(self):
        self.gpioInputMode()
        self.logger.warn ("gpioInput needs to be properly implemented")
        