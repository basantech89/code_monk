x = int(raw_input("Type any integer: "))
p = int(raw_input("Type an integer power: "))
result = 1
def iterativePower(x,p):
	result = 1
	for turn in range(p):
		print "iteration: "+str(turn)+" current result "+str(result)
		result *= x
	return result
z=iterativePower(3,5)
print z
