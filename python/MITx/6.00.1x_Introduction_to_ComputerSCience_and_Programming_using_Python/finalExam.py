def getSublists(L, n):
    lst = []
    count = 0
    for item in L:
	new_lst = []
	for i in range(count, count+n):
       	    new_lst.append(L[i])
       	count += 1
	lst.append(new_lst)
	if count == len(L)-n+1:
            return lst
	
print getSublists([10, 4, 6, 8, 3, 4, 5, 7, 7, 2], 4)
print getSublists([1,1,1,4], 1)
print getSublists([-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20, -21, -22, -23, -24, -25, -26, -27, -28, -29, -30, -31, -32, -33, -34, -35, -36, -37, -38, -39, -40, -41, -42, -43, -44, -45, -46, -47, -48, -49, -50], 5)

def longestRun(L):
    lst = []
    for num in L:
        count = 1
        for i in range(L.index(num), len(L)-1):
            if L[i] <= L[i+1]:
                count += 1
            else:
                break            
        lst.append(count)  
    return max(lst)
                                                
L = [10, 4, 6, 8, 3, 4, 5, 7, 7, 2]   
print longestRun(L)                                                     