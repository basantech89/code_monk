s = 'fhboblfjelibobwjfbobjfdhjbobfj'
total = 0
for i in range(0,len(s)-1):
    if s[i:i+3]=='bob':
         total += 1   
print "Number of times bob occurs is: "+str(total)