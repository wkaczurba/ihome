'''
Created on Mar 6, 2017

@author: WKaczurb
'''

import threading
import time
from datetime import datetime
from timer.TimerSetting import TimerSetting
from Tkconstants import CURRENT

class TimerHandler(object):
    '''
    classdocs
        '''
    
    def __init__(self):
        self.timerSettings = []
        self.observers = []
        self.threadStarted = False        
        self.threadCheckPeriod = 10        
        
    def addObserver(self, observer):
        self.observers.append(observer)
        
    def removeAllObservers(self):
        self.observers=[]
        
    def notifyObservers(self):
        for observer in self.observers:
            observer(self)
    
    def setTimerSettings(self, timerSettings):
        if (not isinstance(timerSettings, (tuple, list))):
            raise TypeError("Invalid type for setTimerSettings(timerSettings). Expected either tuple or list, got: %s" % (str(timerSettings.__class__)))
        
        # TODO: Check that all settings are of type TimerSettings
        for x in timerSettings:
            if (not isinstance(x, TimerSetting)):
                raise TypeError("Invalid type in timerSettings list; Expected TimerSettings; got: %s" % (str(x.__class__)))
        
        self.timerSettings = timerSettings
        
    def getTimerSettings(self):
        return self.timerSettings
    
    def checkIfFits(self, datetime0 = None):
        if (datetime0 == None):
            datetime0 = datetime.now()
        
        for setting in self.timerSettings:
            if (setting.dateTimeMatches(datetime0)):
                return True
        return False
    
    def isOn(self):
        return self.checkIfFits(datetime.now())

    def isOff(self):
        return not self.checkIfFits(datetime.now())
    
    def check(self):
        return self.checkIfFits(datetime.now())
    
    def start(self, condition=None):
        if (self.threadStarted):
            raise RuntimeError("Thread was already started once")
        
        self.thread = threading.Thread(target=self.runner)
        self.threadFinish = False
        self.threadStarted = True
        if (condition == None):
            self.condition = lambda: self.checkIfFits(datetime.now())
        else:
            self.condition = condition
        # Start thread once everything is set..
        self.thread.start()
        
    def stop(self):
        self.threadFinish = True
        while (self.thread.is_alive()):
            ""
                
    def runner(self):
        previous_state = self.condition()
        
        while (not self.threadFinish):
            current_state = self.condition() #self.checkIfFits(datetime.now())
            if (current_state != previous_state):
                #print "runner -> detected change"
                self.notifyObservers()
                previous_state = current_state
            
            time.sleep(self.threadCheckPeriod)
            # TODO: It should calculate the closest time and sleep until then.
            
    def __str__(self, *args, **kwargs):
        s = "<timer.TimerHandler, hash: " + str(self.__hash__()) + "\n"
        for setting in self.timerSettings:
            s += "\t" + str(setting) + "\n"
        
        return s
        #return object.__str__(self, *args, **kwargs)
        
#     def monthMatches(self, date, listOfMonths):
#         month = Month[date.month - 1]
#         result = month in listOfMonths
#         print "monthMatches: " + str(month) + " in " + str(listOfMonths) + "?==" + str(result)
#         return month in listOfMonths
#     
#     def dayMatches(self, date, listOfDays):
#         day = DayOfWeek[date.weekday()]
#         result = day in listOfDays 
#         print "dayMatches: " + str(day) + " in " + str(listOfDays) + "?==" + str(result)
#         return day in listOfDays
#     
#     def dateTimeMatches(self, datetime0, startingTime, endTime, listOfMonths, listOfDays):
#         day = datetime0.date()
#         #day = datetime.date.day()
#         thedaybefore = day - datetime.timedelta(1)
#         
#         #startingTime = datetime.time(x['startingTime'][0], x['startingTime'][1])
#         #endTime = datetime.time(x['endTime'][0], x['endTime'][1])
#         overnight = endTime < startingTime
#         
#         print "Day: " + str(day) + " ("+ str(datetime0.weekday()) +")"
#         print "Time: " + str(datetime0.time())
#         
#         if (overnight):
#             print "overnight: " + str(startingTime) + "["+ str(x['days'])+"] to " + str(endTime) + " (following day)"
#             
#         else:
#             print "daytime: " + str(startingTime) + " to " + str(endTime) + " same day" 
#             
#         # Within hours:
#         if (not overnight):
#             # It has to be the same day:
#             if ((datetime0.time() > startingTime or datetime0.time() < endTime) and 
#                 (self.monthMatches(day, x['months']) and self.dayMatches(day, x['days']))):
#                 return True
#             return False
#         else: # overnight
#             print overnight
#             timeMatcher0 = datetime0.time() < endTime
#             timeMatcher1 = datetime0.time() > startingTime 
#             
#             if ((timeMatcher0 and self.monthMatches(thedaybefore, x['months']) and dayMatches(thedaybefore, x['days'])) or
#                (timeMatcher1  and self.monthMatches(day, x['months']) and self.dayMatches(day, x['days']))):
#                 return True
#             return False
# 
#     def __init__(self, params):
#         '''
#         Constructor
#         '''
        