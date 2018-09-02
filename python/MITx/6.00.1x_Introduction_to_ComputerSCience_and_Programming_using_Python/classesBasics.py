class Coordinate(object):
    def __init__(self, x, y):
        self.x = x
        self.y = y
    def __str__(self):
        return '<'+str(self.x)+', '+str(self.y)+'>'
    def distance(self, other):
        return self.x+other.x-self.y-other.y
    def yo(self):
        return self.__str__().count('3')                  
c = Coordinate(3, 4)      
other = Coordinate(2,1)
print c.yo()  