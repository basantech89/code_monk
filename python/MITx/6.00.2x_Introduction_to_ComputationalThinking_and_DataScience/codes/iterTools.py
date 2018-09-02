import itertools

a = [1, 2, 3, 4]
#boo = itertools.chain.from_iterable(a)



for i in itertools.chain.from_iterable(itertools.combinations(a, r) for r in xrange(len(a)+1)):
    print i
