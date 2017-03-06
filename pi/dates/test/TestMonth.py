'''
Created on Mar 6, 2017

@author: WKaczurb
'''
import unittest
from dates import Month

class TestMonth(unittest.TestCase):

    def testMonth(self):
        self.assertEquals(1, Month.Month.JANUARY)
        self.assertEquals(1, Month.Month.asInteger("JANUARY"))
        self.assertEquals(11, Month.Month.asInteger("NOVEMBER"))
        self.assertEquals("JANUARY", Month.Month.asString(1))
        self.assertEquals("DECEMBER", Month.Month.asString(12))


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()

