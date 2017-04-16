import logging
import requests
import platform

SERVER_BOOT = "http://127.0.0.1:8080"
SERVER_STANDALONE = "http://127.0.0.1:8080/ihome" 

logger = logging.getLogger('server')

# TO ADD CONNECTION INFO, ON DISCONNECTION, ON RECONNECTION
#connected = False

# TODO: Rename to serverReachable?
serverReachable = "UNKNOWN"

# WHAT TO DO WHEN DISCONNECTION HAPPENS:
# Funciton of server is exchange of data; not making it up.
#  So when data is not available -> server should not try to make it, it should set it to [] or None or Null !!!
#
#  - When server is never connected and no settings are given: -> All GPIOs to 0.
#  - When server was gets disconnected [SELECT ONE OR THE OTHER]:
#     - use previous information
#     - or switching all GPIOs to zeros.
#     - or keep working for next 15 minutes hoping that connection gets restored;
#          - if not -> shut down all
#

def onConnected():
    pass
    
def onDisconnected():
    pass

def setServerReachable(newStatus):
    global serverReachable
    if (serverReachable != newStatus and newStatus == True):
        serverReachable = newStatus
        logger.info("Server reachable; serverReachable = True")
        print "onConnected() function call to be added here" # TODO
        
    if (serverReachable != newStatus and newStatus == False):
        serverReachable = newStatus
        logger.warn("Server unreachable; serverReachable = False")
        print "onDisconnected() function call to be added here" #TODO

def getServerReachable():
    return serverReachable

def getName():
    #global server_found
    #server_found = False

    if ("raspberrypi" in platform.uname()):
        possible = ["http://192.168.0.12:8080", "http://192.168.0.12:8080/ihome"]
    else:
        #possible = ["http://192.168.0.100:8080", "http://192.168.0.100:8080/ihome", "http://127.0.0.1:8080", "http://127.0.0.1:8080/ihome"]
        possible = ["http://127.0.0.1:8080", "http://127.0.0.1:8080/ihome"]

    statuses = {}
    
    for x in possible:
        try:
            req = requests.get(x)
            status = req.status_code 
            if (status == 200):
                #server_found = True
                setServerReachable(True)
                return x
            else:
                statuses[x]=status
        except Exception as ex:
            # Capture name of the exception:
            #template = "Exception of type '{0}' occurred. Arguments:\n{1!r}"
            #message = template.format(type(ex).__name__, ex.args)
            statuses[x]=str(type(ex).__name__)
            #logger.error("Error - cannot find server.")
    
    setServerReachable(False)
    logger.debug("Could not find server; statuses: %s" % (statuses))
    #raise RuntimeError("Could not get server")
    raise requests.ConnectionError("Could not find server; statuses: %s" % (statuses))

def get(subpath):
    try:
        retval = requests.get(getName() + subpath)
        setServerReachable(True)
        return retval
    except requests.ConnectionError as ex:
        setServerReachable(False)
        raise ex
    except Exception as ex:
        pass
    
def put(subpath, **kwargs):
    try:
        retval = requests.put(getName() + subpath, **kwargs)
        setServerReachable(True)
        return retval
    except requests.ConnectionError as ex:
        setServerReachable(False)
        raise ex
    except Exception as ex:
        pass
