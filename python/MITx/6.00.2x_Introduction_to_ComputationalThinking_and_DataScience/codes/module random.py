import random
print random.random() #random float between [0, 1)
print random.uniform(1, 10) #random float between [1, 10)
print random.randint(1, 10) #integer from 1 to 10 [1, 10]
print random.randrange(0, 101, 2) #even integers from 0 to 100 [0, 101)
print random.choice('abcdef') #choose a random element
a = [1, 2, 3, 4, 5]
random.shuffle(a)
print a
print random.sample([1, 2, 3, 4, 5], 3) #choose 3 elements
