import datetime
from timer.TimerHandler import TimerHandler
from timer.TimerSetting import TimerSetting 

timerSettings = [TimerSetting(["FRIDAY"], ["MARCH", "MAY"], [10,9], [10,15]),
                  TimerSetting(["FRIDAY"], ["MARCH", "MAY"], [10,20], [10,30])]
 
timerHandler = TimerHandler()
          
def observer():
    timerHandler.check()
    print "Observer called at: " + str(datetime.datetime.now()) +" ; check=" + str(timerHandler.check())

timerHandler.setTimerSettings(timerSettings)
timerHandler.addObserver(lambda: observer())
#timerHandler.notifyObservers()
timerHandler.start()
