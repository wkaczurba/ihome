'''
Created on Mar 7, 2017

@author: WKaczurb
'''

from server.HeatingService import HeatingService
import timer
import pprint

if __name__ == '__main__':
    print "running"
    hs = HeatingService()
    settings = hs.getSettings()
    pprint.pprint(settings)
    #print settings
    
# x = 0;
# while (x < 1):
#     h.getSettings()
#     #print h.settings
#     print "applying settings"
#     h.putStatus([True, False, False])
#     h.putSettings(h.settings)
#     x+=    