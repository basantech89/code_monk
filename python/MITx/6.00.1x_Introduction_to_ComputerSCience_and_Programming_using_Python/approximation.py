''' this code is an way to find square roots of numbers approximately but it wont give result everytime
like in case of 23,bisection search is a better way of to do it.If we take a smaller step size,it will 
take time to find square root and if we take a higher step size,then maybe we could skip the ans
so we use a better algo to do this bisection search.'''

x = 25
epsilon = 0.01
step = epsilon**2
numofguesses = 0
ans = 0.0
while abs(ans**2-x) >= epsilon and ans <= x:
	ans += step
	numofguesses += 1
print "num of guesses: "+str(numofguesses)
if abs(ans**2-x) >= epsilon:
	print "failed on square root of "+str(x)
else:
	print str(ans)+ " is close to the square root of "+str(x)		