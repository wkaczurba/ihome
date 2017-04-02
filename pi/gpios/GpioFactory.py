'''
Created on Apr 2, 2017

There is no typical way of implementing Singleton pattern in python 2.7.
This module mimics singleton behavior

@author: WKaczurb
'''
from gpios.GpioPin import GpioPin

gpioPins = []

def getGpioPin(pinNumber): 
    try:
        gpioPin = next (x for x in gpioPins if x.getPinNumber() == pinNumber)
        
    except StopIteration:
        gpioPin = GpioPin(pinNumber)
        gpioPins.append(gpioPin)
        return gpioPin
    else:
        return gpioPin

        

            