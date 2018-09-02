def findRoot2(x,power,epsilon):
    if x < 0and power % 2 == 0:
        return None
    low = min(0,x)
    high = max(x,0)
    ans = (low+high)/2.0
    while abs(ans**power-x)>epsilon:
        if ans**power<x:
            low = ans
        else:
            high = ans
        ans = (low+high)/2.0
    return ans                        
print findRoot2(-27,3,0.01)    