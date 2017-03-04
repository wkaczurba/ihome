import requests

SERVER_BOOT = "http://127.0.0.1:8080"
SERVER_STANDALONE = "http://127.0.0.1:8080/ihome" 
  
#server = SERVER_BOOT

def getName():
    #possible = { 'boot':"http://127.0.0.1:8080", 'statusIhome':"http://127.0.0.1:8080/ihome" }
    
    
    possible = ["http://127.0.0.1:8080", "http://127.0.0.1:8080/ihome"]
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
