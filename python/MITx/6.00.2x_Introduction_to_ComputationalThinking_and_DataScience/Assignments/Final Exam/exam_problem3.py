import random
import pylab

# Global Variables
MAXRABBITPOP = 1000
CURRENTRABBITPOP = 50
CURRENTFOXPOP = 300

def rabbitGrowth():
    """
    rabbitGrowth is called once at the beginning of each time step.

    It makes use of the global variables: CURRENTRABBITPOP and MAXRABBITPOP.

    The global variable CURRENTRABBITPOP is modified by this procedure.

    For each rabbit, based on the probabilities in the problem set write-up,
      a new rabbit may be born.
    Nothing is returned.
    """
    # you need this line for modifying global variables
    global CURRENTRABBITPOP
    for rabbit in range(CURRENTRABBITPOP):
        Preproduction = 1.0 - (CURRENTRABBITPOP / float(MAXRABBITPOP))
        if CURRENTRABBITPOP < MAXRABBITPOP:
            if Preproduction >= random.random(): CURRENTRABBITPOP += 1


def foxGrowth():
    """
    foxGrowth is called once at the end of each time step.

    It makes use of the global variables: CURRENTFOXPOP and CURRENTRABBITPOP,
        and both may be modified by this procedure.

    Each fox, based on the probabilities in the problem statement, may eat
      one rabbit (but only if there are more than 10 rabbits).

    If it eats a rabbit, then with a 1/3 prob it gives birth to a new fox.

    If it does not eat a rabbit, then with a 1/10 prob it dies.

    Nothing is returned.
    """
    # you need these lines for modifying global variables
    global CURRENTRABBITPOP
    global CURRENTFOXPOP

    for fox in range(CURRENTFOXPOP):
        Pfoxeatrabbit = CURRENTRABBITPOP / float(MAXRABBITPOP)
        if Pfoxeatrabbit >= random.random():
            if CURRENTRABBITPOP > 10:
                CURRENTRABBITPOP -= 1
            if random.random() <= 1.0/3 : CURRENTFOXPOP += 1
        else:
            if random.random() <= 0.9 : CURRENTFOXPOP -= 1


def runSimulation(numSteps):
    """
    Runs the simulation for `numSteps` time steps.

    Returns a tuple of two lists: (rabbit_populations, fox_populations)
      where rabbit_populations is a record of the rabbit population at the
      END of each time step, and fox_populations is a record of the fox population
      at the END of each time step.

    Both lists should be `numSteps` items long.
    """
    rabbitPop, foxPop = [], []
    for step in range(numSteps):
        rabbitGrowth()
        foxGrowth()
        rabbitPop.append(CURRENTRABBITPOP)
        foxPop.append(CURRENTFOXPOP)
    return (rabbitPop, foxPop)


rabbitPop, foxPop = runSimulation(200)
print rabbitPop, "\n", foxPop
pylab.plot(rabbitPop, 'b', label = "rabbit population")
pylab.title("Rabbit v/s Fox Population")
pylab.xlabel("Steps")
pylab.ylabel("Population")
pylab.plot(foxPop, 'r', label = "fox population")
pylab.legend(loc = "best")
pylab.show()

pylab.figure(2)
vals1 = pylab.polyfit(range(len(rabbitPop)), rabbitPop, 2)
vals2 = pylab.polyfit(range(len(foxPop)), foxPop, 2)
pylab.plot(pylab.polyval(vals1, range(len(rabbitPop))), "bo", label = "rabbitPop fit")
pylab.plot(pylab.polyval(vals2, range(len(foxPop))), "ro", label = "foxPop fit")
pylab.legend(loc = "best")
pylab.show()
