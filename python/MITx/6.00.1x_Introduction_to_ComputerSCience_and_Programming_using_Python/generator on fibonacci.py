def genFib():
    fibn_1 = 1
    fibn_2 = 0
    while True:
        #fibn = fibn_1 + fibn_2
        next = fibn_1 + fibn_2
        yield next
        fibn_2 = fibn_1
        fibn_1 = next
fib = genFib()        
print fib        
print fib.next()
print fib.next()
print fib.next()
print fib.next()
print fib.next()
print fib.next()
print fib.next()
print fib.next()
print fib.next()