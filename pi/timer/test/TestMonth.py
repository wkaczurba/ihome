'''
Created on Mar 6, 2017

@author: WKaczurb
'''
import unittest
from timer import Month
from datetime import date

class TestMonth(unittest.TestCase):

    def testMonth(self):
        self.assertEquals(1, Month.Month.JANUARY)
        self.assertEquals(1, Month.Month.asInteger("JANUARY"))
        self.assertEquals(11, Month.Month.asInteger("NOVEMBER"))
        self.assertEquals("JANUARY", Month.Month.asString(1))
        self.assertEquals("DECEMBER", Month.Month.asString(12))
        
        self.assertRaises(ValueError, lambda:  Month.Month.asString(13))
        self.assertRaises(ValueError, lambda: Month.Month.asString(0))
        self.assertRaises(ValueError, lambda: Month.Month.asInteger("XAXUJARY"))
        
        #self.assertRaises(excClass, Month.Month.asString(13))
        
    def testFromDateAsString(self):
        self.assertEquals("MARCH", Month.Month.fromDateToString(date(2017, 3, 7)))


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()

