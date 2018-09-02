def getRatios(v1, v2):
    '''Assume v1 and v2 are lists of equal length of numbers
    returns a list containing the meaningful values of v1[i]/v2[i]'''
    ratios = []
    for index in range(len(v1)):
        try:
            ratios.append(v1[index]/float(v2[index]))
        except ZeroDivisionError:
            ratios.append(float('Nan')) #Nan = not a number
        except:
            raise ValueError('getRatios called with bad args')
    return ratios
print getRatios([1,2,0,3,4], [2,0,3,0,1])
print getRatios([1,2,3], [0,1])                                    