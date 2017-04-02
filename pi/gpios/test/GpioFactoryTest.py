'''
Created on Apr 2, 2017

@author: WKaczurb
'''
import unittest
from gpios import GpioFactory

class Test(unittest.TestCase):


    def testGetGpioPin(self):
        pin2_1 = GpioFactory.getGpioPin(2)
        pin2_2 = GpioFactory.getGpioPin(2)
        self.assertTrue(pin2_1 == pin2_2)


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testGetPin']
    unittest.main()