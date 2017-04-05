'''
Created on Mar 7, 2017

@author: WKaczurb
'''
import unittest
import server.HeatingService
import logging

class TestServer(unittest.TestCase):

#     self.super()
#     logging.basicConfig(format='%(asctime)s [%(name)s]: %(message)s', datefmt='%m/%d/%Y %I:%M:%S %p')
#     self.logger = logging.getLogger('TestServer.py')

    
    def testSystemLoopback(self):
        """Tests the REST Loopback with server"""
        verbose = True # False
        h = server.HeatingService.HeatingService(1)
        h.logger.setLevel(logging.INFO)
        self.assertTrue(h.loopbackTest(100, verbose))
        pass


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testLoopback']
    unittest.main()