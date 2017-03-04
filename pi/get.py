import urllib2
import json

SERVER_BOOT = "http://127.0.0.1:8080"
SERVER_STANDALONE = "http://127.0.0.1:8080/ihome" 
 
 
server = SERVER_BOOT

# SpringBoot:
response = urllib2.urlopen(server + "/current?name=Zoze&connected=true&heatingOn=false&serial=1&temp=2.3").read()


object = json.loads(response)
print object
print object.get('heatingOn')

# Example: http://127.0.0.1:8080/current?name=Zoze&connected=true&heatingOn=false&serial=1&temp=2.3


