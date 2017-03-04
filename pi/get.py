import urllib2
import json
import server

print "This uses urllib2 instead of requests"

# SpringBoot:
response = urllib2.urlopen(server.getName() + "/current?name=Zoze&connected=true&heatingOn=false&serial=1&temp=2.3").read()


object = json.loads(response)
print object
print object.get('heatingOn')

# Example: http://127.0.0.1:8080/current?name=Zoze&connected=true&heatingOn=false&serial=1&temp=2.3


