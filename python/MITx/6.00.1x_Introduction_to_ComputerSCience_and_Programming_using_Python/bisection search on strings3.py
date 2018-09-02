def isIn(char, aStr):
    low = 0
    high = len(aStr)-1
    ans = (low+high)/2
    if len(aStr) == 0:
        return False
    elif aStr[ans] == char:
        return True
    elif aStr[ans] > char:
        return isIn(char, aStr[:ans+1])
    elif aStr[ans] < char:
        return isIn(char, aStr[ans+1:])
print isIn('l', 'abcdef')        