'''
Created on Apr 2, 2017

@author: WKaczurb
'''
import unittest
from gpios.GpioPin import GpioPin

# TODO: Inject into gpioPin a mock that will be called once GpioOutputHigh / GpioOutputLow is called. 

class Test(unittest.TestCase):


    def setUp(self):
        #gpios.GpioPin.GpioPin(2)
        self.pin = GpioPin(2)
        pass


    def tearDown(self):
        pass


    def testGpioOutputHigh(self):
        self.pin.gpioOutputHigh()

    def testGpioOutputLow(self):
        self.pin.gpioOutputLow()


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()