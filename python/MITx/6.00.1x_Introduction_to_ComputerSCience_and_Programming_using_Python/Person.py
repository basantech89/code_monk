import datetime
class Person(object):
    def __init__(self, name):
        self.name = name
        self.birthday = None
        self.lastName = name.split(' ')[-1]
    def __str__(self):
        return self.name
    def getLastName(self):
        return self.lastName
    def setBirthday(self,month,day,year):
        self.birthday = datetime.date(year, month, day)
    def getAge(self):
        if self.birthday == None:
            raise ValueError
        return (datetime.date.today() - self.birthday).days
    def __lt__(self, other):
        if self.lastName == other.lastName:
            return self.name < other.name
        return self.lastName < other.lastName
class MITPerson(Person):
    nextIdNum = 0
    def __init__(self, name):
        Person.__init__(self, name)
        self.idNum = MITPerson.nextIdNum
        MITPerson.nextIdNum += 1
    def __str__(self):
        return str(self.idNum)
    def getIdNum(self):
        return self.idNum
    def __lt__(self, other):
        return self.idNum < other.idNum
class Student(MITPerson):
    pass
class UG(Student):
    def __init__(self, name, classYear):
        MITPerson.__init__(self, name)
        self.year = classYear
    def getClass(self):
        return self.year
class Grad(Student):
    pass
class TransferStudent(Student):
    pass
def isStudent(obj):
    return isinstance(obj, Student)
me = Person('Basant Soni')
print me
print me.getLastName()
me.setBirthday(4, 23, 1994)
print 'Gaurav ',me.getAge()
her = Person('Simran Soni')
print me.__lt__(her)
p1 = MITPerson('Eric')
p2 = MITPerson('John')
p3 = MITPerson('John')
p4 = Person('John')
print p1,p2,p3,p4
print p1 < p3
print p4 < p1
print p1 < p2
