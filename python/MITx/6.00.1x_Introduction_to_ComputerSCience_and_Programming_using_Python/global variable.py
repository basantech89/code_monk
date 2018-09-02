def fibMetered(x):
    '''plz do not set x to be larger then 20 otherwise program will hang'''    
    global numCalls
    numCalls += 1
    if x == 0 or x == 1:
        return 1
    else:
        return fibMetered(x-1) + fibMetered(x-2)

def testFib(n):
    for i in range(n+1):
        global numCalls
        numCalls = 0
        print 'fibonacci of ',i,' = ',fibMetered(i)
        print 'fibonacci called ',numCalls,' times'   
testFib(20)