import requests
import server

# SpringBoot:
#r = requests.get(server + "/current?name=Zoze&connected=true&heatingOn=false&serial=1&temp=2.3")
r = requests.get(server.getName() + "/current?name=Zoze&connected=true&heatingOn=false&serial=1&temp=2.3")
#r = requests.get(server.getName() + "/current")
print r.status_code
print r.headers
#print r.enconding
print r.json()


