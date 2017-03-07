'''
Created on Mar 7, 2017

@author: WKaczurb
'''
import unittest
from timer.TimerSetting import TimerSettings
from datahandler import startingTime
import datetime

class TestTimerSetting(unittest.TestCase):


    def testNormalMatch(self):
        # x = { u'days': [ u'MONDAY', u'SATURDAY', u'FRIDAY', u'SUNDAY'], u'endTime': [5, 34], u'months': [u'FEBRUARY', u'MARCH'], u'startingTime': [21, 16]}
        
        days = ["MONDAY", "FRIDAY"]
        months = ["MARCH", "APRIL"]
        startingTime = [9,20]
        endTime = [13,10]
        
        ts = TimerSettings(days, months, startingTime, endTime)
        dt0 = datetime.datetime(2017, 3, 17, 9, 19) # Monday March
        dt1 = datetime.datetime(2017, 3, 17, 10, 34) # Monday March
        dt2 = datetime.datetime(2017, 3, 17, 13, 34) # Monday March
        
        self.assertFalse(ts.dateTimeMatches(dt0))
        self.assertTrue(ts.dateTimeMatches(dt1))
        self.assertFalse(ts.dateTimeMatches(dt2))
        
        dt0 = datetime.datetime(2017, 3, 18, 9, 19) # Tuesday March
        dt1 = datetime.datetime(2017, 3, 18, 10, 34) # TuesdayMarch
        dt2 = datetime.datetime(2017, 3, 18, 13, 34) # Tuesday March
        
        self.assertFalse(ts.dateTimeMatches(dt0))
        self.assertFalse(ts.dateTimeMatches(dt1))
        self.assertFalse(ts.dateTimeMatches(dt2))
        
        dt0 = datetime.datetime(2017, 2, 17, 9, 19) # Monday February
        dt1 = datetime.datetime(2017, 2, 17, 10, 34) # Monday February
        dt2 = datetime.datetime(2017, 2, 17, 13, 34) # Monday February
        
        self.assertFalse(ts.dateTimeMatches(dt0))
        self.assertFalse(ts.dateTimeMatches(dt1))
        self.assertFalse(ts.dateTimeMatches(dt2))        
        # Overnext month
        pass
    
    def testOvernight(self):
        # OVERNIGHT:
        days = ["MONDAY", "FRIDAY"]
        months = ["MARCH", "APRIL"]
        startingTime = [19,20]
        endTime = [5,10]
        
        ts = TimerSettings(days, months, startingTime, endTime)        
        dtm4 = datetime.datetime(2017, 3, 17, 5, 9) # Monday March
        dtm3 = datetime.datetime(2017, 3, 17, 5, 10) # Monday March
        dtm2 = datetime.datetime(2017, 3, 17, 5, 11) # Monday March
        dtm1 = datetime.datetime(2017, 3, 17, 19, 19) # Monday March
        dt0 = datetime.datetime(2017, 3, 17, 19, 19) # Monday March
        dt1 = datetime.datetime(2017, 3, 17, 19, 20) # Monday March
        dt2 = datetime.datetime(2017, 3, 17, 23, 59) # Monday March
        dt3 = datetime.datetime(2017, 3, 18, 0, 0) # Tuesday March
        dt4 = datetime.datetime(2017, 3, 18, 3, 30) # Tuesday March
        dt5 = datetime.datetime(2017, 3, 18, 5, 9) # Tuesday March
        dt6 = datetime.datetime(2017, 3, 18, 5, 10) # Tuesday March
        dt7 = datetime.datetime(2017, 3, 18, 19, 19) # Tuesday March
        dt8 = datetime.datetime(2017, 3, 18, 19, 20) # Tuesday March
        
        self.assertFalse(ts.dateTimeMatches(dtm4))        
        self.assertFalse(ts.dateTimeMatches(dtm3))        
        self.assertFalse(ts.dateTimeMatches(dtm2))        
        self.assertFalse(ts.dateTimeMatches(dtm1))        
        self.assertFalse(ts.dateTimeMatches(dt0))        
        self.assertTrue(ts.dateTimeMatches(dt1))        
        self.assertTrue(ts.dateTimeMatches(dt2))        
        self.assertTrue(ts.dateTimeMatches(dt3))        
        self.assertTrue(ts.dateTimeMatches(dt4))        
        self.assertTrue(ts.dateTimeMatches(dt5))        
        self.assertFalse(ts.dateTimeMatches(dt6))        
        self.assertFalse(ts.dateTimeMatches(dt7))        
        self.assertFalse(ts.dateTimeMatches(dt8))    
        
    def testOverboundaries(self):
        # OVERNIGHT (Saturday 31 December -> Sunday 1 January)
        days = ["SATURDAY"]
        months = ["DECEMBER"]
        startingTime = [19,20]
        endTime = [5,10]
        
        ts = TimerSettings(days, months, startingTime, endTime)        
        dt0 = datetime.datetime(2016, 12, 31, 19, 19) # Saturday Deceber
        dt1 = datetime.datetime(2016, 12, 31, 19, 20) # Saturday Deceber
        dt2 = datetime.datetime(2016, 12, 31, 23, 59) # Saturday Deceber
        dt3 = datetime.datetime(2017, 1, 1, 0, 0) # Sunday January
        dt5 = datetime.datetime(2017, 1, 1, 5, 9) # Sunday January
        dt6 = datetime.datetime(2017, 1, 1, 5, 10) # Sunday January
        dt7 = datetime.datetime(2017, 1, 1, 19, 19) # Sunday January
        dt8 = datetime.datetime(2017, 1, 1, 19, 20) # Sunday January
           
        self.assertFalse(ts.dateTimeMatches(dt0))        
        self.assertTrue(ts.dateTimeMatches(dt1))        
        self.assertTrue(ts.dateTimeMatches(dt2))        
        self.assertTrue(ts.dateTimeMatches(dt3))        
        
        self.assertTrue(ts.dateTimeMatches(dt5))        
        self.assertFalse(ts.dateTimeMatches(dt6))        
        self.assertFalse(ts.dateTimeMatches(dt7))        
        self.assertFalse(ts.dateTimeMatches(dt8))                    

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()