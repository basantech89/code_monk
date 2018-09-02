def removeDups(L1, L2):
    for e in L1:
        if e in L2:
            L1.remove(e)
    return L1            
L1 = [1,2,3,4]
L2 = [1,2,5,6]
print removeDups(L1, L2)            

def removeDupsBetter(L1, L2):
    L = L1[:]
    for e in L:
        if e in L2:
            L1.remove(e)
    return L1
print removeDupsBetter(L1, L2)                
            
