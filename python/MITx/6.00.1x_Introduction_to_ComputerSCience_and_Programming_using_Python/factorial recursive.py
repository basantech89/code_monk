def factR(n):
    '''assume that n is an int >0 return n'''
    if n == 1:
        return n#base case
    return n*factR(n-1)#recursive case
print factR(5)          
        