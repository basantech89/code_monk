# -*- coding: utf-8 -*-
class Frob(object):
    def __init__(self, name):
        self.name = name
        self.before = None
        self.after = None
    def setBefore(self, before):
        # example: a.setBefore(b) sets b before a
        self.before = before
    def setAfter(self, after):
        # example: a.setAfter(b) sets b after a
        self.after = after
    def getBefore(self):
        return self.before
    def getAfter(self):
        return self.after
    def myName(self):
        return self.name
        
def insert(atMe, newFrob):
    """
    atMe: a Frob that is part of a doubly linked list
    newFrob:  a Frob with no links 
    This procedure appropriately inserts newFrob into the linked list that atMe is a part of.    
    """
    if atMe.myName() >= newFrob.myName():
        if atMe.getBefore() != None:
            return insert(atMe.getBefore(), newFrob)
        newFrob.setAfter(atMe)
        atMe.setBefore(newFrob)
    else:
        if atMe.getAfter() != None:
            newFrob.setAfter(atMe.getAfter())
            atMe.getAfter().setBefore(newFrob)                        
        atMe.setAfter(newFrob)
        newFrob.setBefore(atMe) 
    lst = []
    lst.append(atMe.myName())
    for i in range(0, 3):
        if atMe.getAfter() != None:
            lst.append(atMe.getAfter().myName())
            atMe = atMe.getAfter()
        else:
            break                            
    print lst
eric = Frob('eric')
andrew = Frob('andrew')
ruth = Frob('ruth')
fred = Frob('fred')
martha = Frob('martha')

insert(eric, andrew)
insert(eric, ruth)
insert(eric, fred)
insert(eric, martha)