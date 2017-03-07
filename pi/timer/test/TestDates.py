'''
Created on Mar 6, 2017

@author: WKaczurb
'''
import unittest


class TestDates(unittest.TestCase):

    def test2(self):
        self.assertEquals(True, True, "True is true.")
        self.assertNotEquals(True, False, "False True is not true.")
        print "test2 done"
        
    #def testInvalid(self):
        #self.assertTrue(False, "induced error")

    def testDates(self):
        self.assertEquals(True, True, "True is true.")
        self.assertNotEquals(True, False, "False True is not true.")
        print "testDates done"
    


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()

