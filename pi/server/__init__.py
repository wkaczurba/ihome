import requests
import os

SERVER_BOOT = "http://127.0.0.1:8080"
SERVER_STANDALONE = "http://127.0.0.1:8080/ihome" 
  
#server = SERVER_BOOT

def getName():

    if ("raspberrypi" in os.uname()):
        possible = ["http://192.168.0.12:8080", "http://192.168.0.12:8080/ihome"]
    else:
        possible = ["http://192.168.0.100:8080", "http://192.168.0.100:8080/ihome"]

    statuses = {}
    
    for x in possible:
        status = requests.get(x).status_code 
        if (status == 200):
            return x
        else:
            statuses[x]=status
    
    print "Could not get server; statuses:"
    print statuses
        
    raise RuntimeError("Could not get server")
