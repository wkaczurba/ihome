import timer
#from datetime import date
#from timer.LocalDate import LocalDate
import datetime
from cookielib import DAYS
#import datetime.timedelta
#import datetime.date


x = { u'days': [ u'MONDAY', u'SATURDAY', u'FRIDAY', u'SUNDAY'], u'endTime': [5, 34], u'months': [u'FEBRUARY', u'MARCH'], u'startingTime': [21, 16]}

print x 
                                              
DayOfWeek = ("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY")
Month = ("JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER")


print DayOfWeek[0]
print DayOfWeek.index("MONDAY")

print "GOT:" 
now = datetime.datetime.now() # TODO for tests -> randomize this datetime.
currentMonth     = Month[now.month - 1] # String
currentDayOfWeek = DayOfWeek[now.weekday()] # String
currentHour = str(now.hour)  # Integer
currentMinute = str(now.minute)

# Printing
print "Month: " + currentMonth
print "DayOfWeek: "  + currentDayOfWeek
print "Hour: " + str(currentHour)
print "Minutes: " + str(currentMinute) # Integer

###########################
# Matching:
# #for ()
# print "----------"
# if (currentMonth in x['months']):
#     print "Month matches"
#       
# if (currentDayOfWeek in x['days']): # TODO: Handle the case when timer runs overnight.
#     print "Day matches"
    
    
def monthMatches(date, listOfMonths):
    month = Month[date.month - 1]
    result = month in listOfMonths
    print "monthMatches: " + str(month) + " in " + str(listOfMonths) + "?==" + str(result)
    return month in listOfMonths

def dayMatches(date, listOfDays):
    day = DayOfWeek[date.weekday()]
    result = day in listOfDays 
    print "dayMatches: " + str(day) + " in " + str(listOfDays) + "?==" + str(result)
    return day in listOfDays

def dateTimeMatches(datetime0, startingTime, endTime, listOfMonths, listOfDays):
    day = datetime0.date()
    #day = datetime.date.day()
    thedaybefore = day - datetime.timedelta(1)
    
    #startingTime = datetime.time(x['startingTime'][0], x['startingTime'][1])
    #endTime = datetime.time(x['endTime'][0], x['endTime'][1])
    overnight = endTime < startingTime
    
    print "Day: " + str(day) + " ("+ str(datetime0.weekday()) +")"
    print "Time: " + str(datetime0.time())
    
    if (overnight):
        print "overnight: " + str(startingTime) + "["+ str(x['days'])+"] to " + str(endTime) + " (following day)"
        
    else:
        print "daytime: " + str(startingTime) + " to " + str(endTime) + " same day" 
        
    # Within hours:
    if (not overnight):
        # It has to be the same day:
        if ((datetime0.time() > startingTime or datetime0.time() < endTime) and 
            (monthMatches(day, x['months']) and dayMatches(day, x['days']))):
            return True
        return False
    else: # overnight
        print overnight
        timeMatcher0 = datetime0.time() < endTime
        timeMatcher1 = datetime0.time() > startingTime 
        
        if ((timeMatcher0 and monthMatches(thedaybefore, x['months']) and dayMatches(thedaybefore, x['days'])) or
           (timeMatcher1  and monthMatches(day, x['months']) and dayMatches(day, x['days']))):
            return True
        return False
    
    #print datetime0.time()
    
    #print endTime
    #if ()
    
    #print timerMonth
    #if (timerMonth)

startingTime = datetime.time(x['startingTime'][0], x['startingTime'][1])
endTime = datetime.time(x['endTime'][0], x['endTime'][1])

fits = dateTimeMatches(datetime.datetime.now() + datetime.timedelta(hours = 13), 
                startingTime,
                endTime,
                x['months'], x['days'])

print "fits: " + str(fits)






