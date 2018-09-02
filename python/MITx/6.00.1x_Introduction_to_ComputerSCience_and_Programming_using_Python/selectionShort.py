'''The complexity of this algo is quadratic - square of length of list
So it's not a good sorting algo, we can do better
It will be really expensive to first sort and then search, searching will be good but sorting is expensive.
O(sort(L)) + O(log(n)) < O(len(L))
the equation comes out to be False in this case''' 

L = [8, 6, 4, 5, 9, 7]
def selSort(L):
    for i in range(len(L)-1):
        minIndx = i
        minVal = L[i]
        j = i+1
        while j < len(L):
            if minVal > L[j]:
                minIndx = j
                minVal = L[j]
            j += 1
        temp = L[i]
        L[i] = L[minIndx]
        L[minIndx] = temp
    return L
print selSort(L)                                        
                                
                    