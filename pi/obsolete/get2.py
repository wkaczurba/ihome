import requests
import server

# SpringBoot:
#r = requests.get(server + "/current?name=Zoze&connected=true&heatingOn=false&serial=1&temp=2.3")

def testGet1():
    r = requests.get(server.getName() + "/current?name=Zoze&connected=true&heatingOn=false&serial=1&temp=2.3")
    #r = requests.get(server.getName() + "/current")
    print r.status_code
    print r.headers
    #print r.enconding
    print r.json()
    
def testGet2():
    r = requests.get(server.getName() + "/testget2")
    print r.json()
    settings = r.json()
    zones = settings.get('zones')

    i = 2;
    print zones[i]
    if (zones[i].get("mode") == "MANUAL"):
        print "MANUAL MODE-> Heating On? == %s" % (zones[i].get("manualModeSetting"))


def testGetReadback():
    r = requests.get(server.getName() + "/testreadback")
    print r.json()
    pass

#testGet1()
#testGet2()
testGetReadback()
