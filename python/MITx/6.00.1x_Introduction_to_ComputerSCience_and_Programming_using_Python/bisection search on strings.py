def isIn(char, aStr):
    low = 0
    high = len(aStr)-1
    ans = (low+high)/2
    i = 0
    while i < len(aStr) and len(aStr) != 0:
        if char == aStr[i]:
            if aStr[ans] == char:
                return True
            elif aStr[ans] > char:
                return isIn(char, aStr[:ans+1])
            elif aStr[ans] < char:
                return isIn(char, aStr[ans+1:])
        else:
            i += 1
    if len(aStr) == 0:
        return False
    elif char != aStr[i-1]:
        return False            
        
        
print isIn('a', 'abcdef')      
    