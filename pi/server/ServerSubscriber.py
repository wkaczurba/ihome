# This is the main class for heating
import server
#import requests
import logging
import pprint
#import time
from requests import ConnectionError 

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
        newSettings = {}
        try:
            r = server.get("/settings/%d" % (self.device))
            status = r.status_code
            if (status != 200):
                self.logger.error("/settings/%d did not work, http.status=%s" % (self.device, status))
            newSettings = r.json()                
        except ConnectionError:
            self.logger.debug("/settings/%d did not work (ConnectionError); returning empty settings" % (self.device))
        finally:
            if (self.currentSettings != newSettings):
                self.currentSettings = newSettings
                self.logger.info( "Settings changed: " + pprint.pformat(newSettings, 2) )
                self.notifySettingsChangeObservers()
            return self.currentSettings
    
    # statuses, e.g,:
    def putStatusREST(self, statuses = []): 
        data = {u'heatingOn': statuses}
        try:
            r = server.put("/status/%d" % (self.device), json=data)
            # TODO: The code should be different.
            status = r.status_code
            if (status != 200):
                self.logger.error('/status/%d (put) did not work, http.status=%s' % (self.device, status))
            else:
                self.logger.debug("status put ok")
        except ConnectionError:
            self.logger.debug("/settings/%d (put) did not work (ConnectionError);" % (self.device))
            
    def putSettingsREST(self, settings):
        self.logger.debug("putting:\n" + pprint.pformat(settings, 2))
        try:
            r = server.put("/settingsFeedback/%d" % (self.device), json=settings)
            status = r.status_code
            if (status != 200):
                self.logger.error('/settingsFeedback/%d did not work, http.status=%s' % (self.device, status))
            else:
                self.logger.debug("putSettingsREST -> fbSettings put ok")
        except ConnectionError:
            self.logger.debug('/settingsFeedback/%d did not work (ConnectionError)' % (self.device))
            
    def loopbackTest(self, count=1000, verbose=False):
        i = count
        try:
            while (i > 0):
                # Getting:
                r = server.get("/test/heatingSeetingloopback")
                status = r.status_code
                if (status != 200):
                    self.logger.error("/test/heatingSeetingloopback did not work, http.status=%s" % (status))
                    return False
                retrievedSettings = r.json()
        
                if (verbose):
                    self.logger.info(pprint.pformat(retrievedSettings, 2))
    
                # Putting
                r = server.put("/test/heatingSeetingloopback", json=retrievedSettings)
                status = r.status_code
                if (status != 200):
                    self.logger.error('/settingsFeedback/0 did not work, http.status=%s' % (status))
                    return False
                i-=1
            self.logger.info( "Passed" + str(count) )
            return True
        except ConnectionError:
            self.logger.error("/settingsFeedback/0 did not work (ConnectionError)")


