'''
Created on Mar 6, 2017

@author: WKaczurb
'''

# class MyClass(object):
#     '''
#     classdocs
#         '''
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
        