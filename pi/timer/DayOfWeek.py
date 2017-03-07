'''
Created on Mar 6, 2017

@author: WKaczurb
'''
from datetime import date

class DayOfWeek():
    '''
    classdocs
    '''
    MONDAY = 1
    TUESDAY = 2
    WEDNESDAY = 3
    THURSDAY = 4
    FRIDAY = 5
    SATURDAY = 6
    SUNDAY = 7
    DaysOfWeek = ("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY") 

    def __init__(self, params):
        '''
        Constructor
        '''    
    # n=> 1=MONDAY, 7=SUNDAY
    @staticmethod
    def asString(n):
        if (n < 1 or n > 7):
            raise ValueError("n must be between 1 and 7; it is n==" + str(n))
        return DayOfWeek.DaysOfWeek[n - 1]

    @staticmethod
    def asInteger( s):
        return DayOfWeek.DaysOfWeek.index(s)+1
    
    @staticmethod
    def fromDateToString(date0):
        assert(isinstance(date0, date))
        return DayOfWeek.asString(date0.isoweekday())
    