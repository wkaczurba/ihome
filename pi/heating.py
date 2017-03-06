# This is the main class for heating
import server
import requests
import logging
import pprint
#import time

class Heating:
    
    def __init__(self):
        logging.basicConfig(format='%(asctime)s [%(name)s]: %(message)s', datefmt='%m/%d/%Y %I:%M:%S %p')
        self.logger = logging.getLogger('Heating.py')
        
        self.zones= []
        self.settings = []
        self.status = []
        self.getSettings()
        self.logger.warning("setRelays function missing.")
        self.logger.warning("Function to calculate what GPIO to set missing") # TODO: add function
        
        
    def getSettings(self):
        r = requests.get(server.getName() + "/settings/0")
        status = r.status_code
        if (status != 200):
            self.logger.error("/settings/0 did not work, http.status=%s" % (status))
            
        newSettings = r.json()
        if (self.settings != newSettings):
            self.logger.info( "Settings changed: " + pprint.pformat(newSettings, 2) )
            print "Settings changed" + pprint.pformat(newSettings, 2)
        
        # Apply:
        self.settings = newSettings
        pass
    
    # statuses, e.g,:
    def putStatus(self, statuses = []): 
        data = {u'heatingOn': statuses}
        r = requests.put(server.getName() + "/status/0", json=data)
        # TODO: The code should be different.
        status = r.status_code
        if (status != 200):
            self.logger.error('/status/0 (put) did not work, http.status=%s' % (status))
        else:
            self.logger.debug("status put ok")
            
    def putSettings(self, settings):
        self.logger.debug("putting:\n" + pprint.pformat(settings, 2))
        r = requests.put(server.getName() + "/settingsFeedback/0", json=settings)
        status = r.status_code
        if (status != 200):
            self.logger.error('/settingsFeedback/0 did not work, http.status=%s' % (status))
        else:
            self.logger.debug("putSettings -> fbSettings put ok")
            
    def setRelays(self, statuses=[]):
        # TODO: Missing function
        pass
        
    def update(self):
        self.getSettings()
        
        # TODO: Add function that calculates what to turn on/off
                
        self.setRelays()
        
        self.putStatus(self.status)
        self.putSettings(self.settings)
        
        # Get settings from server
        # Make decision: what to heat, what to not
        # Send back settings to server
        # Send back the heating statuses
        pass
    
    def loopbackTest(self, count=1000):
        i = count
        while (i > 0):
            # Getting:
            r = requests.get(server.getName() + "/test/heatingSeetingloopback")
            status = r.status_code
            if (status != 200):
                self.logger.error("/test/heatingSeetingloopback did not work, http.status=%s" % (status))
                return
            retrievedSettings = r.json()
    
            print pprint.pformat(retrievedSettings, 2)

            # Putting
            r = requests.put(server.getName() + "/test/heatingSeetingloopback", json=retrievedSettings)
            status = r.status_code
            if (status != 200):
                self.logger.error('/settingsFeedback/0 did not work, http.status=%s' % (status))
                return
            i-=1
        print "Passed" + str(count)


h = Heating()

h.logger.setLevel(logging.INFO)
h.loopbackTest()
#h.logger.

# 
# x = 100
# while x > 0:
#     time.sleep(1)
#     h.update()
#     x-=1



# x = 0;
# while (x < 1):
#     h.getSettings()
#     #print h.settings
#     print "applying settings"
#     h.putStatus([True, False, False])
#     h.putSettings(h.settings)
#     x+=1
