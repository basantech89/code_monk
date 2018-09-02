class intSet(object):
    '''intSet is a set of integers
    The value is represented by a list of ints self.vals
    Each int in the set occurs in self.vals exactly once'''
    def __init__(self):
        '''Create an empty set of integers'''        
        self.vals = []
    def insert(self, e):
        '''Assume e is an integer and insert e into self.vals'''
        if not e in self.vals:
            self.vals.append(e)
    def __str__(self):
        '''Returns a string representation of self'''
        self.vals.sort()
        return '{'+','.join([str(e) for e in self.vals])+'}'
    def member(self, e):
        '''Assume e is an integer
        Return true if e is in self.vals and false otherwis'''
        return e in self.vals
    def remove(self ,e):
        '''Assume e is an integer and removes e from self
        Raise ValueError if e is not in self.vals'''
        try:
            self.vals.remove(e)
        except:
            raise ValueError(str(e)+' not found')                            
s = intSet()
print s      
s.insert(5)
s.insert(3)
s.insert(9)
print s.member(3)
s.remove(5)
print s.__str__()                                    
        