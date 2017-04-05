'''
Created on Mar 7, 2017

@author: WKaczurb
'''
import unittest
import server.ServerSubscriber
import logging

class TestServerSubscriber(unittest.TestCase):

    def testSystemLoopback(self):
        """Tests the REST Loopback with server"""
        verbose = True # False
        h = server.ServerSubscriber.ServerSubscriber(1)
        h.logger.setLevel(logging.INFO)
        self.assertTrue(h.loopbackTest(100, verbose))
        pass


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testLoopback']
    unittest.main()