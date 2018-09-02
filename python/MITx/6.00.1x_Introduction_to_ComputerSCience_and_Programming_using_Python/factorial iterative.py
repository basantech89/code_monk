def factl(n):
	''' this function will give you the factorial of any number...its an iterative way to get factorial'''
	result = 1
	while n > 0:
		result *= n
		n -= 1
	return result		
print factl(6)		
		