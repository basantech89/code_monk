def getSubjectStats(subject, weights):
    return [[elt[0], elt[1], avg(elt[1], weights)]
        for elt in subject]
#def avg(grades,weights):
#   return dotProduct(grades, weights)/len(grades)
def avg(grades, weights):
    try:
        return dotProduct(grades, weights)/len(grades)
    except ZeroDivisionError:
        print 'no grades data'
        return 0.0
    except TypeError:
        newgr = [convertLetterGrade(elt) for elt in grades]
        return dotProduct(newgr, weights)/len(newgr)
def dotProduct(a, b):
    result = 0.0
    for i in range(len(a)):
        result  += a[i]*b[i]
    return result
def convertLetterGrade(grade):
    if type(grade) == int:
        return grade
    elif grade == 'A':
        return 90
    elif grade == 'B':
        return 80
    elif grade == 'C':
        return 70
    elif grade == 'D':
        return 60
    else:
        return 50                                                
test = [[['fred', 'flintstone'], [10.0, 5.0, 85.0, 'D']], [['barney', 'rubble'], [10.0, 'B', 74.0, 'H']], [['wilima', 'flintstone'], [8.0, 10.0, 96.0]], [['dino'], []]]
weights = [.3, .2, .5, .35]
print getSubjectStats(test, weights)

                                    