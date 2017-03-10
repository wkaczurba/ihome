import time

def func(x):
    time.sleep(2)
    print "f2"
    x()
    
def update():
    print "Update"
    pass


func(lambda: update())
