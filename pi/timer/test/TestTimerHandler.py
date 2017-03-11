'''
Created on Mar 10, 2017

@author: WKaczurb
'''
import unittest
import timer
import time
from datetime import datetime
from datetime import timedelta
from timer.TimerSetting import TimerSetting
from timer.TimerHandler import TimerHandler
from timer.Month import Month
from timer.DayOfWeek import DayOfWeek

class Test(unittest.TestCase):

    def testCheckIfFits(self):
        timerSettings = [TimerSetting(["MONDAY", "WEDNESDAY"], ["MARCH", "MAY"], [17,05], [5,43]),
                         TimerSetting(["FRIDAY"], ["MARCH", "MAY"], [6,00], [6,10])]
         
        timerHandler = TimerHandler()
        timerHandler.setTimerSettings(timerSettings)
        self.assertFalse(timerHandler.checkIfFits(datetime(2017, 3, 10, 8, 41))) # March Friday 8:41
        self.assertTrue(timerHandler.checkIfFits(datetime(2017, 3, 10, 6, 05))) # March Friday 8:41
        self.assertFalse(timerHandler.checkIfFits(datetime(2017, 3, 10, 5, 59))) # March Friday 8:41
         
        self.assertTrue(timerHandler.checkIfFits(datetime(2017, 3, 9, 5, 42))) # March Thursday 5:42
        self.assertFalse(timerHandler.checkIfFits(datetime(2017, 3, 9, 5, 44))) # March Thursday 5:44
        self.assertTrue(timerHandler.checkIfFits(datetime(2017, 3, 8, 17, 12))) # March Wednesday 17:12
        self.assertFalse(timerHandler.checkIfFits(datetime(2017, 3, 8, 17, 04))) # March Wednesday 17:12
        pass
     
    def getRetVal(self):
        #print "getRetVal called"
        return self.retVal
    
    def observer(self):
        print "Received update"
        self.observerNotification += 1
        
    def observerLambda1(self):
        #print "observed"
        self.notified1 = True
         
    def testObserverNotifiation(self):
        self.notified1 = False
        timerHandler = TimerHandler()
        timerHandler.addObserver(lambda: self.observerLambda1())
        timerHandler.notifyObservers()
        self.assertTrue(self.notified1, "Received no notification; observer was not called.")
        #print "testObserverNotifiation done"
    
    def testThread(self):
        # Create timerSettings with dateTime.now() + 0.1sec.
        # Set period to minimal; 
        # Check if called.
        self.retVal = True
        self.observerNotification = 0
        
        timerHandler = TimerHandler()
        timerHandler.threadCheckPeriod = 0.05
        timerHandler.addObserver(lambda: self.observer())
        #timerHandler.notifyObservers()
        print "Starting..."
        timerHandler.start(condition=lambda:self.getRetVal())
        
        print "main: sleeping 1..."
        time.sleep(1)
        
        print "main: retval = False...; sleeping=1"
        self.retVal = False
        time.sleep(1)
        self.assertEquals(1, self.observerNotification) # 1st Notification: True -> False
        
        print "main: retval = True...; sleeping=1"
        self.retVal = True
        time.sleep(1)
        self.assertEquals(2, self.observerNotification) # 2st Notification: False -> True

        print "main: retval = False...; sleeping=1"
        self.retVal = False
        time.sleep(1)
        self.assertEquals(3, self.observerNotification) # 3rd Notification: True -> False
        
        print "stoping timer. observerNotification=" + str(self.observerNotification)
        timerHandler.stop()


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()