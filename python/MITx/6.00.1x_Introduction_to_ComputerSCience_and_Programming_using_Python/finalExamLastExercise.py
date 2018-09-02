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
    def getEverything(self):
        print self.name
        print self.before.myName()
        print self.after.myName()        
    
def insert(atMe, newFrob):
    """
    atMe: a Frob that is part of a doubly linked list
    newFrob:  a Frob with no links 
    This procedure appropriately inserts newFrob into the linked list that atMe is a part of.    
    """
    if atMe.getBefore() == None and atMe.getAfter() == None:
        if atMe.myName() >= newFrob.myName():
            newFrob.setAfter(atMe)
            atMe.setBefore(newFrob)
        else:
            atMe.setAfter(newFrob)
            newFrob.setBefore(atMe)
    if atMe.getBefore() == None and atMe.getAfter() != None:
        if atMe.myName() >= newFrob.myName():
            newFrob.setAfter(atMe)
            atMe.setBefore(newFrob)
        else:
            return insert(atMe.getAfter(), newFrob)
    if atMe.getBefore() != None and atMe.getAfter() == None:
        if atMe.myName() <= newFrob.myName():
            atMe.setAfter(newFrob)
            newFrob.setBefore(atMe)
        else:
            return insert(atMe.getBefore(), newFrob)
    if atMe.getBefore() != None and atMe.getAfter != None and atMe.myName() < newFrob.myName():
        if atMe.myName() < newFrob.myName():
            return insert(atMe.getAfter(), newFrob)
        else:
            newFrob.setAfter(atMe)
            atMe.setBefore(newFrob)
    if atMe.getBefore() != None and atMe.getAfter != None and atMe.myName() > newFrob.myName():                                                                                                                                                  
        if atMe.myName() > newFrob.myName():
            return insert(atMe.getBefore(), newFrob)
        else:
            atMe.setAfter(newFrob)
            newFrob.setBefore(atMe)                    
            
eric = Frob('eric')
andrew = Frob('andrew')
ruth = Frob('ruth')
fred = Frob('fred')
martha = Frob('martha')

insert(eric, andrew)
insert(eric, ruth)
insert(eric, fred)
