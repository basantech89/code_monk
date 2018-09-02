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
        if atMe.getAfter() != None:
            atMe.getBefore().setAfter(newFrob)
            newFrob.setBefore(atMe.getBefore())
        newFrob.setAfter(atMe)
        atMe.setBefore(newFrob)
    else:
        if atMe.getAfter() != None:
            return insert(atMe.getAfter(), newFrob)
        else:
            atMe.setAfter(newFrob)
            newFrob.setBefore(atMe)
    if atMe.getAfter() == None:
        if atMe.myName() <= newFrob.myName():
            atMe.getAfter().setBefore(newFrob)
            newFrob.setAfter(atMe.getAfter())
            atMe.setAfter(newFrob)
            newFrob.setBefore(atMe)                    
                                            
                                                                                                            
    if atMe.getBefore() != None and atMe.getAfter() == None:
        if atMe.myName() <= newFrob.myName():
            atMe.setAfter(newFrob)
            newFrob.setBefore(atMe)
        else:
            return insert(atMe.getBefore(), newFrob)                         
    lst = []
    lst.append(atMe.myName())
    for i in range(0, 2):
        if atMe.getBefore() != None:
            lst.insert(0, atMe.getBefore().myName())
            print atMe.myName()
            atMe = atMe.getBefore()
            print atMe.getBefore().myName()
        else:
            break                    
        
eric = Frob('eric')
andrew = Frob('andrew')
ruth = Frob('ruth')
fred = Frob('fred')
martha = Frob('martha')

insert(eric, andrew)

