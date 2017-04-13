'''
Created on Apr 2, 2017

There is no typical way of implementing Singleton pattern in python 2.7.
This module mimics singleton behavior

@author: WKaczurb
'''
import platform

from gpios.GpioPin import GpioPin

gpioPins = []

# For setting mode:
if ("raspberrypi" in platform.uname()):
    import RPi.GPIO as GPIO
else:
    import RPiDummy.GPIO as GPIO
GPIO.setmode(GPIO.BCM)
        

def getGpioPin(pinNumber): 
    try:
        gpioPin = next (x for x in gpioPins if x.getPinNumber() == pinNumber)
        
    except StopIteration:
        gpioPin = GpioPin(pinNumber)
        gpioPins.append(gpioPin)
        return gpioPin
    else:
        return gpioPin

        

def getInstantiatedGpioPins():
    """In the order they where instantiated"""
    return gpioPins
            