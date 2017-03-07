'''
Created on Mar 7, 2017

@author: WKaczurb
'''

from timer.Month import Month
from timer.DayOfWeek import DayOfWeek
from datetime import datetime
from datetime import time
from datetime import date
from datetime import timedelta
from datahandler import dayMatches

class TimerSettings(object):
    '''
    classdocs
    '''

    # x = { u'days': [ u'MONDAY', u'SATURDAY', u'FRIDAY', u'SUNDAY'], u'endTime': [5, 34], u'months': [u'FEBRUARY', u'MARCH'], u'startingTime': [21, 16]}
    def __init__(self, days, months, startingTime, endTime):
        '''
        Constructor
        '''
        #if (not days.__class__ in [tuple, list]):
        
        if (not isinstance(days, (tuple, list))):
            raise TypeError("days argument must be a tuple or list; it is: " + str(days))
        
        if (not isinstance(months, (tuple, list))): 
            raise TypeError("months argument must be a tuple or list; it is: " + str(months))

        if (not isinstance(startingTime, (tuple, list))): 
            raise TypeError("startingTime argument must be a tuple or list; it is: " + str(startingTime))

        if (not isinstance(endTime, (tuple, list))): 
            raise TypeError("endTime argument must be a tuple or list; it is: " + str(endTime))
        
        # Additional checks; should raise exception if invalid value:
        all(Month.asInteger(x) for x in months)
        all(DayOfWeek.asInteger(x) for x in days)
        
        self.days = days
        self.months = months
        self.startingTime = startingTime
        self.endTime = endTime
        
        #x = { u'days': [ u'MONDAY', u'SATURDAY', u'FRIDAY', u'SUNDAY'], u'endTime': [5, 34], u'months': [u'FEBRUARY', u'MARCH'], u'startingTime': [21, 16]} 
        
    def dateTimeMatches(self, datetime0):
        if (not isinstance(datetime0, datetime)):
            raise TypeError("dateTime0 must be a datetime.datetime object")
        
#        day = datetime0.date()

        day = datetime0.date() #.isoday
        overnight = self.endTime < self.startingTime
        startingTime = time(self.startingTime[0], self.startingTime[1])
        endTime = time(self.endTime[0], self.endTime[1]) 
        #print "Day: " + str(day) + " ("+ str(datetime0.weekday()) +")"
        #print "Time: " + str(datetime0.time())
        
        if (overnight):
            print "overnight: " + str(startingTime) + "["+ str(self.days) +"] to " + str(endTime) + " (following day)"
             
        else:
            print "daytime: " + str(startingTime) + " to " + str(endTime) + " same day on days: " + str(self.days) + " in months: " + str(self.months)

        # Within hours:
        if (not overnight):
            weekdayMatches = DayOfWeek.fromDateToString(day) in self.days
            monthMatches = Month.fromDateToString(day) in self.months
            print "weekdayMatches: " + str(weekdayMatches)
            print "monthMatches: " + str(monthMatches)
            print "time: " + str(datetime0.time())
                              
            # It has to be the same day:
            if ((datetime0.time() >= startingTime and 
                datetime0.time() < endTime) and 
#                (self.monthMatches(day, x['months']) and self.dayMatches(day, x['days']))):
                (weekdayMatches and monthMatches)):
                return True
            return False
        else: # overnight
            print overnight
                         
            # Day it should start:
            timeMatcher1 = datetime0.time() >= startingTime 
            weekDayMatches1 = DayOfWeek.fromDateToString(day) in self.days
            monthMatches1 = Month.fromDateToString(day) in self.months
            if (timeMatcher1 and weekDayMatches1 and monthMatches1):
                return True
            
            # Is it the second day?
            timeMatcher2 = datetime0.time() < endTime
            thedaybefore = day - timedelta(1)
            weekDayMatches2 = DayOfWeek.fromDateToString(thedaybefore) in self.days
            monthMatches2 = Month.fromDateToString(thedaybefore) in self.months
            if (timeMatcher2 and weekDayMatches2 and monthMatches2):
                return True
            
            return False

    