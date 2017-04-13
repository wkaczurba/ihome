'''
Created on Apr 12, 2017

@author: wkaczurb
'''
import logging

logger = logging.getLogger('RPiDummy.GPIO')
logger.info("GPIO module imported")

BCM = "BCM"
OUT = "OUT"
IN = "IN"
HIGH = "HIGH"
LOW = "LOW"

def setmode(value):
    logger.info("setmode(%s) called" % (value))

def setup(pin, mode):
    logger.info("setup(%d, %s)" % (pin, mode))

def output(pin, value):
    logger.info("output(%d, %s)" % (pin, value))

def input(pin):
    logger.info("input(%d)" % (pin))
