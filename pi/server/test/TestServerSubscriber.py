'''
Created on Mar 7, 2017

@author: WKaczurb
'''
import unittest
import server.ServerSubscriber
import logging
from server.ServerSubscriber import ServerSubscriber

class TestHeatingService(unittest.TestCase):
	
	def observerFunction(self):
		print "observerFunction called"
		self.observerCalled = True
	
	def testNotifiers(self):
		hs = ServerSubscriber(1)
		hs.addSettingsChangeObserver(lambda: self.observerFunction())
		hs.notifySettingsChangeObservers()
		print locals()
		self.assertTrue(hasattr(self, 'observerCalled'))
		self.assertTrue(self.observerCalled)
		pass


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testLoopback']
    unittest.main()
	