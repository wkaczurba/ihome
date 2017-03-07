import requests
import server


def testReadback1():
    data = {u'heatingOn': False, u'serial': 1, u'connected': True, u'name': u'Zoze', u'temp': 2.3}
    
    r = requests.put(server.getName() + "/currentput", json=data)
    # When with authentication:
    # r = requests.put(server.getName() + "/currentput", json=data, auth=("user", "pass"))
    
    print r.status_code
    print r.content
    
# statuses, e.g,:
def putStatus(statuses = []): 
    data = {u'heatingOn': statuses}
    r = requests.put(server.getName() + "/status/0", json=data)
    r.content
    print r.status_code
        

#def sendReadback(list):   
#    r = requests.put(server.getName() + "/currentput", json=data)
#print data;

#testReadback1()
putStatus([True, False, False])

