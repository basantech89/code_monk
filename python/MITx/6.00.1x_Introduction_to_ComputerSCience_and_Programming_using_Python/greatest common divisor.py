def gcdIter(a, b):
    '''
    a, 1b: positive integers
    
    returns: a positive integer, the greatest common divisor of a & b.
    '''
    # Your code here
    result = min(a,b)
    while result > 0:
        if a % result == 0 and b % result == 0:
            return result
        else:
            result -= 1
print gcdIter(17,12)           