def fib(x):
    '''assume x an int >= 0
return fibonacci of x'''
    assert type(x)==int and x>=0
    if x==0 or x==1:#two base cases
        return 1
    else:
	return fib(x-1)+fib(x-2)#recursive step
print fib(15)