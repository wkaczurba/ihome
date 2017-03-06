'''
Created on Mar 6, 2017

@author: WKaczurb
'''

class Month(object):
    '''
    classdocs
    '''
    JANUARY = 1
    FEBRUARY = 2
    MARCH = 3
    APRIL = 4
    MAY = 5
    JUNE = 6 
    JULY = 7
    AUGUST = 8 
    SEPTEMBER = 9
    OCTOBER = 10
    NOVEMBER = 11
    DECEMBER = 12
    Months = ("JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER")
    
    def __init__(self, params):
        '''
        Constructor
        '''
        
    # n=> 1 = JANUARY, 12 = DECEMBER
    @staticmethod
    def asString(n):
        return Month.Months[n - 1]

    @staticmethod
    def asInteger( s):
        return Month.Months.index(s)+1
        