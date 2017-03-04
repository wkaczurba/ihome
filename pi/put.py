import requests
import server

data = {u'heatingOn': False, u'serial': 1, u'connected': True, u'name': u'Zoze', u'temp': 2.3}

r = requests.put(server.getName() + "/currentput", json=data)
# When with authentication:
# r = requests.put(server.getName() + "/currentput", json=data, auth=("user", "pass"))

print r.status_code
print r.content


#print data;
