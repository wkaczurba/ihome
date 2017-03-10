# This is the main class for heating
import server
import requests
import logging
import pprint
#import time

class HeatingService:
    
    def __init__(self):
        logging.basicConfig(format='%(asctime)s [%(name)s]: %(message)s', datefmt='%m/%d/%Y %I:%M:%S %p')
        self.logger = logging.getLogger('Heating.py')
        
        self.zones= []
        self.settings = []
        self.settingsChangeObservers = []
        self.status = []
        self.getSettingsREST()
        self.logger.warning("setRelays function missing.")
        self.logger.warning("Function to calculate what GPIO to set missing") # TODO: add function
        
    def addSettingsChangeObserver(self, func):
        """Adds lambda functions that will be called once settings are changed"""
        self.settingsChangeObservers.append(func)
        
    def notifySettingsChangeObservers(self):
        for observer in self.settingsChangeObservers:
            observer()
            
    def getSettings(self):
        return self.settings
        
    def getSettingsREST(self):
        r = requests.get(server.getName() + "/settings/0")
        status = r.status_code
        if (status != 200):
            self.logger.error("/settings/0 did not work, http.status=%s" % (status))
            
        newSettings = r.json()
        if (self.settings != newSettings):
            self.settings = newSettings
            self.logger.info( "Settings changed: " + pprint.pformat(newSettings, 2) )
            print "Settings changed" + pprint.pformat(newSettings, 2)
            self.notifySettingsChangeObservers()
        
        # Apply:
        #self.settings = newSettings
        
        return self.settings
    
    # statuses, e.g,:
    def putStatusREST(self, statuses = []): 
        data = {u'heatingOn': statuses}
        r = requests.put(server.getName() + "/status/0", json=data)
        # TODO: The code should be different.
        status = r.status_code
        if (status != 200):
            self.logger.error('/status/0 (put) did not work, http.status=%s' % (status))
        else:
            self.logger.debug("status put ok")
            
    def putSettingsREST(self, settings):
        self.logger.debug("putting:\n" + pprint.pformat(settings, 2))
        r = requests.put(server.getName() + "/settingsFeedback/0", json=settings)
        status = r.status_code
        if (status != 200):
            self.logger.error('/settingsFeedback/0 did not work, http.status=%s' % (status))
        else:
            self.logger.debug("putSettingsREST -> fbSettings put ok")
            
    def setRelays(self, statuses=[]):
        # TODO: Missing function
        pass
        
    def update(self):
        self.getSettingsREST()
        
        # TODO: Add function that calculates what to turn on/off
                
        self.setRelays()
        
        self.putStatusREST(self.status)
        self.putSettingsREST(self.settings)
        
        # Get settings from server
        # Make decision: what to heat, what to not
        # Send back settings to server
        # Send back the heating statuses
        pass
    
    def loopbackTest(self, count=1000, verbose=False):
        i = count
        while (i > 0):
            # Getting:
            r = requests.get(server.getName() + "/test/heatingSeetingloopback")
            status = r.status_code
            if (status != 200):
                self.logger.error("/test/heatingSeetingloopback did not work, http.status=%s" % (status))
                return False
            retrievedSettings = r.json()
    
            if (verbose):
                print pprint.pformat(retrievedSettings, 2)

            # Putting
            r = requests.put(server.getName() + "/test/heatingSeetingloopback", json=retrievedSettings)
            status = r.status_code
            if (status != 200):
                self.logger.error('/settingsFeedback/0 did not work, http.status=%s' % (status))
                return False
            i-=1
        print "Passed" + str(count)
        return True



#h.logger.

# 
# x = 100
# while x > 0:
#     time.sleep(1)
#     h.update()
#     x-=1



# x = 0;
# while (x < 1):
#     h.getSettingsREST()
#     #print h.settings
#     print "applying settings"
#     h.putStatusREST([True, False, False])
#     h.putSettingsREST(h.settings)
#     x+=1

