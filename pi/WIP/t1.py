'''
Created on Apr 19, 2017

@author: wkaczurb
'''

import time

def createZoneHandlers():
    print "Create Zones function called..."
    
def updateZonesWithCurrentData():
    print "updateZonesWithCurrentData() Called"
    
def updateZonesWithPreviousData():
    print "updateZonesWithPreviousData() called"

def updateZonesWithEmptyData():
    print "updateZonesWithEmptyData() called"
                
    
# TODO: Implement proper state design pattern when have more time.
class UpdateTimeZonesSimpleFSM:
    
    def __init__(self):
        self.TIMEOUT = 1
        self.OldState = None
        self.states = {
            "START" : self.startState,
            "UPDATE" : self.updateState }

        self.nextState = "START"
        self.lastUpdateTimestamp = None
        
    def updateTimestamp(self):
        self.lastUpdateTimestamp = time.time()
        
    def isTimeOut(self):
        return (time.time() - self.lastUpdateTimestamp >= self.TIMEOUT)
    
    def isSettingValid(self, s):
        if (not isinstance(s, dict)):
            return False
        
        if (len(s) == 0):
            return False
        return True
    
    def run(self, settings):
        if (self.OldState != self.nextState):
            print "STATE SET TO: %s" % (self.nextState)
            self.OldState = self.nextState
        self.states[self.nextState](settings)

    def startState(self, settings):
        if (self.isSettingValid(settings)):
            createZoneHandlers()
            self.updateTimestamp()
            self.nextState = "UPDATE"
            print "Zones created; Next states -> Regular updates"
        else:
            self.nextState = "START"        
            print "Waiting for valid data."

        
    def updateState(self, settings):
        if (self.isSettingValid(settings)):
            updateZonesWithCurrentData()
            self.updateTimestamp()
            print "  Zones updated."
        else:
            if (not self.isTimeOut()):
                print "  ** No date but NO TIMEOUT YET ** using old data"
                updateZonesWithPreviousData()
            else:
                print "  ** Timed out -> updating with empty data **"
                updateZonesWithEmptyData()
                
        self.nextState = "UPDATE"

            

if __name__ == '__main__':
    fsm = UpdateTimeZonesSimpleFSM();
    fsm.run(None)
    fsm.run(None)
    fsm.run(None)
    fsm.run(None)
    fsm.run({ 1 : "" })        
    fsm.run({ 2 : "" })        
    fsm.run({ 3 : "" })        
    fsm.run(None)
    fsm.run(None)
    fsm.run(None)
    fsm.run(None)
    fsm.run({ 1 : "" })        
    fsm.run({ 1 : "" })        
    fsm.run(None)
    time.sleep(2)
    fsm.run(None)
    time.sleep(2)
    fsm.run(None)
    time.sleep(2)
    fsm.run(None)
    time.sleep(2)
    fsm.run(None)
    fsm.run({ 1 : "" })        
    fsm.run({ 1 : "" })   
    fsm.run({ 1 : "" })        
    fsm.run({ 1 : "" })           
    pass