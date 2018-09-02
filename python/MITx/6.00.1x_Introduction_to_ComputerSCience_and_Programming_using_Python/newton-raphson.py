epsilon = 0.01
y = float(raw_input("enter any number: "))
guess = y/2.0
while abs(guess**2-y) >= epsilon:
	guess = guess - (((guess**2)-y)/(2*guess))
	print guess
print 'Square root of '+str(y)+' is about '+str(guess)
