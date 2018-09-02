class Item(object):
    def __init__(self, n, v, w):
        self.name = n
        self.value = float(v)
        self.weight = float(w)
    def getName(self):
        return self.name
    def getValue(self):
        return self.value
    def getWeight(self):
        return self.weight
    def __str__(self):
        return '<' + self.name + ', ' + str(self.value) + ', '\
                + str(self.weight) + '>'

def buildItems():
    names = ['clock', 'painting', 'radio', 'vase', 'book', 'computer']
    vals = [175, 90, 20, 50, 10, 200]
    weights = [10, 9, 4, 2, 1, 20]
    Items = []
    for i in range(len(vals)):
        Items.append(Item(names[i], vals[i], weights[i]))
    return Items

def greedy(Items, maxWeight, keyFcn):
    assert type(Items) == list and maxWeight >= 0
    ItemsCopy = sorted(Items, key = keyFcn, reverse = True)
    result, totalVal, totWeight, i = [], 0.0, 0.0, 0
    while totWeight < maxWeight and i < len(Items):
        if totWeight + ItemsCopy[i].getWeight() <= maxWeight:
            result.append(ItemsCopy[i])
            totWeight += ItemsCopy[i].getWeight()
            totalVal += ItemsCopy[i].getValue()
        i += 1
    return (result, totalVal)

def value(item):
    return item.getValue()

def weightInverse(item):
    return 1.0 / item.getWeight()

def density(item):
    return item.getValue() / item.getWeight()

def testGreedy(Items, constraint, getKey):
    taken, val = greedy(Items, constraint, getKey)
    print 'Total value of items taken = ' + str(val)
    for item in taken:
        print ' ', item


def testGreedys(maxWeight = 20):
    Items = buildItems()
    print 'Items to choose from:'
    for item in Items:
        print ' ', item
    print 'Use greedy by value to fill a knapsack of size ', maxWeight
    testGreedy(Items, maxWeight, value)
    print 'Use greedy by weight to fill a knapsack of size ', maxWeight
    testGreedy(Items, maxWeight, weightInverse)
    print 'Use greedy by density to fill a knapsack of size, ', maxWeight
    testGreedy(Items, maxWeight, density)

#testGreedys()



#knapsack 0/1
def dToB(n, numDigits):
    """requires: n is a natural number less then 2**nunumDigits returns a binary
    string of length numDigits representing the decimal number n"""
    assert type(n) == int and type(numDigits) == int and n >= 0 and \
            n < 2**numDigits
    bStr = ''
    while n > 0:
        bStr = str(n % 2) + bStr
        n = n // 2
    while numDigits - len(bStr) > 0:
        bStr = '0' + bStr
    return bStr


def genPset(Items):
    """Generate a list of lists representing the power set of Items"""
    numSubsets = 2**len(Items)
    #Creating binary vector V
    templates = []
    for i in range(numSubsets):
        templates.append(dToB(i, len(Items)))
    pset = []
    for t in templates:
        elem = []
        for j in range(len(t)):
            if t[j] == '1':
                elem.append(Items[j])
        pset.append(elem)
    return pset



def chooseBest(pset, constraint, getVal, getWeight):
    bestVal = 0.0
    bestSet = None
    for Items in pset:
        ItemsVal = 0.0
        ItemsWeight = 0.0
        for item in Items:
            ItemsVal += getVal(item)
            ItemsWeight += getWeight(item)
        if ItemsWeight <= constraint and ItemsVal > bestVal:
            bestVal = ItemsVal
            bestSet = Items
    return (bestSet, bestVal)


def testBest():
    Items = buildItems()
    pset = genPset(Items)
    taken, val = chooseBest(pset, 20, Item.getValue, Item.getWeight)
    print 'Total values of items taken = ' + str(val)
    for item in taken:
        print ' ', item


#testBest()


#Binary Tree -
#Root node has no parent
#Every non-root node has exactly one parent and at most two branches or children
#A childless node is called a leaf

#Recursive implementation
def maxVal(toConsider, avail):
    """It's using the decision tree to generate equivalent of those vectors that
    represents all the different choices of including things.
    It does not explore the entire power set, As soon as it finds a choice that
    is going to exceed the constarint, it stops exploring that part of tree
    and that will improve the efficiency of this algorithm."""
    if toConsider == [] or avail == 0:
        result = (0, ())
    #No left branch
    elif toConsider[0].getWeight() > avail:
        result = maxVal(toConsider[1:], avail)
    #There's a left branch
    else:
        nextItem = toConsider[0]
        #Explore left branch
        withVal, withToTake = maxVal(toConsider[1:], avail - nextItem.getWeight())
        withVal += nextItem.getValue()
        #Explore right branch
        withoutVal, withoutToTake = maxVal(toConsider[1:], avail)
        #Choose best branch
        if withVal > withoutVal:
            result = (withVal, withToTake + (nextItem,))
        else:
            result = (withoutVal, withoutToTake)
    return result


def smallTest():
    Items = buildItems()
    val, taken = maxVal(Items, 20)
    for item in taken:
        print item
    print 'Total valuse of item taken = ' + str(val)


smallTest()
