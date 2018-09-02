def recurMul(a,b):
	if b == 1:
		return a	# base case
	else:
		return a + recurMul(a,b-1)	# recursive step
recurMul(3,5)		