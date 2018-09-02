def findRoot3(x,power,epsilon):
    if x < 0and power % 2 == 0:
        return None
    low = min(-1,x)
    high = max(x,1)
    ans = (low+high)/2.0
    while abs(ans**power-x)>epsilon:
        if ans**power<x:
            low = ans
        else:
            high = ans
        ans = (low+high)/2.0
    return ans                        
print findRoot3(0.25,2,0.001)    