def uniqueValues(aDict):
    '''
    aDict: a dictionary
    return string
Write a Python function that returns a list of keys in aDict that map to 
integer values that are unique (i.e. values appear exactly once in aDict). 
The list of keys you return should be sorted in increasing order. (If aDict
does not contain any unique values, you should return an empty list.)
    '''
    # Your code here
    dic_list = []
    for key in aDict:
        new_dic_values = aDict.values()
        new_dic_values.remove(aDict[key])
        for num in new_dic_values:
            if aDict[key] == num:
                break
        else:
            dic_list.append(key)
    dic_list.sort()                        
    return dic_list
print uniqueValues({1: 1, 2: 1, 3: 3})