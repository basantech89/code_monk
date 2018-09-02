def howMany(aDict):
    x = 0
    for i in range(0,len(aDict)):
        for a in range(0,len(aDict.values()[i])):
            x += 1
    return x            
print howMany({'a': ['apple', 'aardvark','anteater'], 'b': ['babbon', 'cat'], 1 : ['yoyo']})
