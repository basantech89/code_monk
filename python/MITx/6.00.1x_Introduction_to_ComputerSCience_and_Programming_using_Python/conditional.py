# branching program
x = int(raw_input("enter an integer:"))
if x%2 == 0:
	if x%3 == 0:
		print(" ")
		print("divisible by 2 and 3")
	else:
		print(" ")
		print("divisible by 2 but not by 3")
elif x%3 == 0:
	print("divisible by 3 but not by 2")
else:
	print("neither divisible by 2 nor by 3")			
print('done with conditional')