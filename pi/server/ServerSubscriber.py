# This is the main class for heating
import server
import requests
import logging
import pprint
#import time

class ServerSubscriber:
    
    def __init__(self, device):
        #logging.basicConfig(format='%(asctime)s [%(name)s]: %(message)s', datefmt='%m/%d/%Y %I:%M:%S %p')
        self.logger = logging.getLogger('ServerSubscriber.py')
        
        self.device = device
        #self.zones= []
        self.currentSettings = []
        self.settingsChangeObservers = []
        self.getSettingsAndNotifyREST()
        self.logger.debug("Started heatingService for device=%d" % (device))
        
    def addSettingsChangeObserver(self, func):
        """Adds lambda functions that will be called once settings are changed"""
        self.settingsChangeObservers.append(func)
        
    def notifySettingsChangeObservers(self):
        for observer in self.settingsChangeObservers:
            observer()
            
    def getSettings(self):
        return self.currentSettings
        
    def getSettingsAndNotifyREST(self):
        r = requests.get(server.getName() + "/settings/%d" % (self.device))
        status = r.status_code
        if (status != 200):
            self.logger.error("/settings/%d did not work, http.status=%s" % (self.device, status))
            
        newSettings = r.json()
        if (self.currentSettings != newSettings):
            self.currentSettings = newSettings
            self.logger.info( "Settings changed: " + pprint.pformat(newSettings, 2) )
            #print "Settings changed" + pprint.pformat(newSettings, 2)
            self.notifySettingsChangeObservers()
        return self.currentSettings
    
    # statuses, e.g,:
    def putStatusREST(self, statuses = []): 
        data = {u'heatingOn': statuses}
        r = requests.put(server.getName() + "/status/%d" % (self.device), json=data)
        # TODO: The code should be different.
        status = r.status_code
        if (status != 200):
            self.logger.error('/status/%d (put) did not work, http.status=%s' % (self.device, status))
        else:
            self.logger.debug("status put ok")
            
    def putSettingsREST(self, settings):
        self.logger.debug("putting:\n" + pprint.pformat(settings, 2))
        r = requests.put(server.getName() + "/settingsFeedback/%d" % (self.device), json=settings)
        status = r.status_code
        if (status != 200):
            self.logger.error('/settingsFeedback/%d did not work, http.status=%s' % (self.device, status))
        else:
            self.logger.debug("putSettingsREST -> fbSettings put ok")
            
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
                self.logger.info(pprint.pformat(retrievedSettings, 2))

            # Putting
            r = requests.put(server.getName() + "/test/heatingSeetingloopback", json=retrievedSettings)
            status = r.status_code
            if (status != 200):
                self.logger.error('/settingsFeedback/0 did not work, http.status=%s' % (status))
                return False
            i-=1
        self.logger.info( "Passed" + str(count) )
        return True


