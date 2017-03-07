'''
Created on Mar 7, 2017

@author: WKaczurb
'''
import unittest
from timer import DayOfWeek 
from datetime import date

class Test(unittest.TestCase):


    def testDayOfWeek(self):
        self.assertEquals(1, DayOfWeek.DayOfWeek.MONDAY)
        self.assertEquals(7, DayOfWeek.DayOfWeek.SUNDAY)
        self.assertEquals(1, DayOfWeek.DayOfWeek.asInteger("MONDAY"))
        self.assertEquals(7, DayOfWeek.DayOfWeek.asInteger("SUNDAY"))
        self.assertEquals("MONDAY", DayOfWeek.DayOfWeek.asString(1))
        self.assertEquals("SUNDAY", DayOfWeek.DayOfWeek.asString(7))        
        self.assertRaises(ValueError, lambda: DayOfWeek.DayOfWeek.asString(8))
        self.assertRaises(ValueError, lambda: DayOfWeek.DayOfWeek.asString(-2))
        self.assertRaises(ValueError, lambda: DayOfWeek.DayOfWeek.asInteger("ZZZ"))
        #print DayOfWeek.DayOfWeek.asInteger("ASD")
        #self.assertRaises(IndexError, lambda: DayOfWeek.DayOfWeek.asInteger("ASD"))

    def testFromDateAsString(self):
        self.assertEquals("TUESDAY", DayOfWeek.DayOfWeek.fromDateToString(date(2017, 3, 7)))

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()