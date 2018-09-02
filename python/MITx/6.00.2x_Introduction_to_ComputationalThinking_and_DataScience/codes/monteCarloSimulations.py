import random

def rollDie():
    return random.choice([1, 2, 3, 4, 5, 6])

#Pascal's math
def checkPascal(numTrials, roll):
    yes = 0.0
    for i in range(numTrials):
        for j in range(24):
            d1 = roll()
            d2 = roll()
            if d1 == 6 and d2 == 6:
                yes += 1
                break
    print 'probability of loosing =',1.0 - yes/numTrials

def rollLoadedDie():
    if random.random() < 1.0/5.5:
        return 6
    else:
        return random.choice([1, 2, 3, 4, 5])

#checkPascal(10000, rollDie)
#checkPascal(10000, rollLoadedDie)

#flipping a coin
def flip(numFlips):
    heads = 0
    for i in range(numFlips):
        if random.random() < 0.5:
            heads += 1
    return heads/float(numFlips)

for i in range(5): #number of trials
    print flip(1000000)


def noReplacementSimulation(numTrials):
    '''
    Runs numTrials trials of a Monte Carlo simulation
    of drawing 3 balls out of a bucket containing
    3 red and 3 green balls. Balls are not replaced once
    drawn. Returns the a decimal - the fraction of times 3
    balls of the same color were drawn.
    '''
    yes = 0.0
    for i in range(numTrials):
        bucket = ['R', 'R', 'R', 'B', 'B', 'B']
        random.shuffle(bucket)
        draw = random.sample(bucket, 3)
        if draw == ['R', 'R', 'R'] or draw == ['B', 'B', 'B']:
            yes += 1
    return yes/numTrials

print noReplacementSimulation(10)
