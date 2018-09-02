def strToInt(s):
    number = ''
    for c in s:
        number += str(ord(c))
    index = int(number)
    return index

def hashStr(s, tableSize = 101):
    number = ''
    for c in s:
        number += str(ord(c))
    index = int(number) % tableSize
    return index

#print strToInt('Basant is way cool')
#print hashStr('Basant is way cool')

class intDict(object):
    '''A dictioanry with integer keys'''

    def __init__(self, numBuckets):
        '''Create an empty dictionary'''
        self.buckets = []
        self.numBuckets = numBuckets
        for i in range(numBuckets):
            self.buckets.append([])

    def addEntry(self, dictKey, dictVal):
        '''Assumes dictkey an int, Adds an entry.'''
        hashBucket = self.buckets[dictKey % self.numBuckets]
        for i in range(len(hashBucket)):
            if hashBucket[i][0] == dictKey:
                continue
        hashBucket.append((dictKey, dictVal))

    def getValue(self, dictKey):
        '''Assumes dictKey an int.
        Returns entry associated with key dictKey'''
        hashBucket = self.buckets[dictKey % self.numBuckets]
        for e in hashBucket:
            if e[0] == dictKey:
                return e[1]
        return None

    def __str__(self):
        res = ''
        for b in self.buckets:
            for t in b:
                res = res + str(t[0]) + ':' + str(t[1]) + ','
        return '{' + res[:-1] + '}'

import random

'''D = intDict(29)
for i in range(20):
    #choose a random int in range(10**5)
    key = random.choice(range(10**5))
    D.addEntry(key, i)

print '\n', 'The buckets are:'
for hashBucket in D.buckets: #violates abstraction barrier
    print ' ',hashBucket
print D'''

def collision_prob(numBuckets, numInsertions):
    '''
    Given the number of buckets and the number of items to insert,
    calculates the probability of a collision.
    '''
    prob = 1.0
    for i in range(1, numInsertions):
        prob = prob * ((numBuckets - i) / float(numBuckets))
    return 1 - prob

print collision_prob(1000, 50)
print collision_prob(1000, 200)
print collision_prob(365, 30)
print collision_prob(365, 250)
print collision_prob(365, 56)

for i in range(365):
    prob = collision_prob(365, i)
    if prob >= 0.99:
        print(prob, i)
        break


def sim_insertions(numBuckets, numInsertions):
    '''
    Run a simulation for numInsertions insertions into the hash table.
    '''
    choices = range(numBuckets)
    used = []
    for i in range(numInsertions):
        hashVal = random.choice(choices)
        if hashVal in used:
            return False
        else:
            used.append(hashVal)
    return True

def observe_prob(numBuckets, numInsertions, numTrials):
    '''
    Given the number of buckets and the number of items to insert,
    runs a simulation numTrials times to observe the probability of a collision.
    '''
    probs = []
    for t in range(numTrials):
        probs.append(sim_insertions(numBuckets, numInsertions))
    print probs
    return 1 - sum(probs)/float(numTrials)

observe_prob(1000, 50, 10)
#print observe_prob(1000, 200, 1000)


def main():
    hash_table = intDict(25)
    hash_table.addEntry(15, 'a')
#    random.seed(1) # Uncomment for consistent results
    for i in range(20):
       hash_table.addEntry(int(random.random() * (10 ** 9)), i)
    hash_table.addEntry(15, 'b')
    print hash_table.buckets  #evil
    print '\n', 'hash_table =', hash_table
    print hash_table.getValue(15)

#main()
