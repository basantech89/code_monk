a = [1, 2, 3, 4, 5]
b = '{'+', '.join(str(e) for e in a)+'}'
print b
def intersect(b):
    new = []
    for i in b:
        try:
            new.append(int(i))
        except:
            raise ValueError()
    return new
print intersect(b)        
                                
