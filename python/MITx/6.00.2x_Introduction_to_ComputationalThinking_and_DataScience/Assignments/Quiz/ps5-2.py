import random

def drawing_without_replacement_sim(numTrials):
    '''
    Runs numTrials trials of a Monte Carlo simulation
    of drawing 3 balls out of a bucket containing
    4 red and 4 green balls. Balls are not replaced once
    drawn. Returns a float - the fraction of times 3
    balls of the same color were drawn in the first 3 draws.
    '''

    balls = ['r', 'r', 'r', 'r', 'g', 'g', 'g', 'g']
    for trial in xrange(numTrials):
        random.shuffle(balls)
        drwan = [random.choice(balls) for i in xrange(3)]
        if drwan == ['r'] * 3 or drwan == ['g'] * 3:
            return 1 / float(7)

print drawing_without_replacement_sim(1000)
