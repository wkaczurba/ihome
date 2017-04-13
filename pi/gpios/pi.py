#import RPiDummy.GPIO as GPIO
import platform

if ("raspberrypi" in platform.uname()):
    import RPi.GPIO as GPIO
else:
    import RPiDummy.GPIO as GPIO 

import time

GPIO.setmode(GPIO.BCM)

GPIO.setup(2, GPIO.OUT)
GPIO.setup(3, GPIO.OUT)
GPIO.setup(4, GPIO.OUT)

for i in range(0, 20):
    GPIO.output(2, GPIO.HIGH)
    GPIO.output(3, GPIO.HIGH)
    GPIO.output(4, GPIO.HIGH)

    time.sleep(0.2)
    
    GPIO.output(2, GPIO.LOW)
    GPIO.output(3, GPIO.LOW)
    GPIO.output(4, GPIO.LOW)

    time.sleep(0.2)


# '''
# Created on Apr 11, 2017
# 
# @author: wkaczurb
# '''
# 
# 
# class MyClass(object):
#     '''
#     classdocs
#     '''
# 
# 
#     def __init__(self, params):
#         '''
#         Constructor
#         '''
#         