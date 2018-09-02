def genTest():
    yield 1
    yield 2
foo = genTest()
print foo.next()  
print foo.next() 
for n in genTest():
    print n