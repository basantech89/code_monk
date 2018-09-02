def applyToEach(L, f):
    ''' assume L is a list, f is a function
    mutate L by replacing each element'''
    for i in range(len(L)):
        L[i] = f(L[i])
    return L        
L = [6, 1, 3]
def fact(n):
    if n == 1:
        return 1
    return n*fact(n-1)
def fib(n):
    if n == 0 or n == 1:
        return 1
    return fib(n-1)+fib(n-2)
print applyToEach(L, abs)
print applyToEach(L, int)
print applyToEach(L, fact)
print applyToEach(L, fib)

def applyFuns(L, x):
    #L supposed to be a list of functions
    for f in L:
        print f(x)
applyFuns([abs, int, fact, fib], 4)
L1 = [9, 2, 8]
L2 = [4, 3, 45]
# more generalize built-in higher order procedure is map - an n-array function(a fun that takes n args) and n collection of arguments

print map(min, L1, L2)
          
            
        