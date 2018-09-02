def lenRecur(aStr):
    global count
    count = 0
    lengthRecur(aStr)
    print 'length of the string is ',count
def lengthRecur(aStr):
    global count    #global variable
    if aStr == '':
        return 0    #base case
    else:
        count += 1
        return lengthRecur(aStr[1:])    #recursive step
lenRecur('abcdef') 