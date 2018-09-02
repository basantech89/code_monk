import numpy

def stdDev(X):
    mean = sum(X)/float(len(X))
    tot = 0.0
    for x in X:
        tot += (x-mean)**2
    return (tot/len(X))**0.5

def CV(X):
    mean = sum(X)/float(len(X))
    try:
        return stdDev(X)/mean
    except ZeroDivisionError:
        return float('NaN')

def mean(X):
    sum = 0
    for x in X : sum += x
    return sum / len(X)

def variance(X):
    mu = mean(X)
    temp = 0
    for x in X : temp += (x - mu)**2
    return temp / len(X)

A = [0,1,2,3,4,5,6,7,8]

B = [5,10,10,10,15]

C = [0,1,2,4,6,8]

D = [6,7,11,12,13,15]

E = [9,0,0,3,3,3,6,6]

def cal():
    X = [A, B, C, D, E]
    for x in X:
        print "mean and variance for ",str(x), " is ", mean(x), "and", variance(x)
cal()
