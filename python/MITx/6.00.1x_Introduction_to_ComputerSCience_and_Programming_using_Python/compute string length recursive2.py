def lenRecur(aStr):
    global count    #global variable
    count = 0
    def lengthRecur(aStr):
        global count   
        if aStr == '':
            return 0    #base case
        else:
            count += 1
            return lengthRecur(aStr[1:])    #recursive step
    lengthRecur(aStr)
    print 'length of the string is ',count   
   
lenRecur('abcdef')