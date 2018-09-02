def genPrimes():
    prime = 3
    prime_list = [2]
    yield 2
    while True:
        for num in prime_list:
            if prime % num == 0:
                break
            else:
                pass
        if prime % num != 0:         
            yield prime
            prime_list.append(prime) 
        prime += 1

# without generators
'''for n in range(2, 10):
     for x in range(2, n):
         if n % x == 0:
            print n, 'equals', x, '*', n/x
            break
     else:
          print n, 'is a prime number'
          '''
                                                    
foo = genPrimes()
print foo.next()
print foo.next()
print foo.next()
print foo.next()
print foo.next()
print foo.next()
print foo.next()
print foo.next()
print foo.next()
print foo.next()
print foo.next()
print foo.next()