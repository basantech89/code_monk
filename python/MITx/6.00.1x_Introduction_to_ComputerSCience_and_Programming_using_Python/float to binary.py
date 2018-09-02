x = float(raw_input("enter a float between 0 and 1 : "))
p = 0
while(x*2**p)%1 != 0:
	print("remainder is "+str(x*2**p-int(x*2**p))
	p += 1
num = int(x*2**p)
result = ""
if num == 0:
	result = '0'
while num > 0:
	result = str(num%2) + result
	num = num/2
for i in range(p-len(result)):
	result = '0' + result
result = result[0:-p]+"."+result[-p:]
print("the binary representation of the float "+str(x)+" is "+"str(result))							