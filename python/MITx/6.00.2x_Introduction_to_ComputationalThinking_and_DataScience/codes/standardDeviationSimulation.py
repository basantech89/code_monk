import random
import pylab

def stdDev(X):
    mean = sum(X)/float(len(X))
    tot = 0.0
    for x in X:
        tot += (x-mean)**2
    return (tot/len(X))**0.5

def runTrial(numFlips):
    numHeads = 0
    for n in range(numFlips):
       if random.random() < 0.5:
           numHeads += 1
    numTails = numFlips - numHeads
    return numHeads, numTails

def flipPlot(minExp, maxExp, numTrials):
    meanRatios, meanDiffs, ratiosSds, diffsSds = [], [], [], []
    xAxis = []
    for exp in range(minExp, maxExp+1):
        xAxis.append(2**exp)
    for numFlips in xAxis:
        ratios = []
        diffs = []
        for t in range(numTrials):
            numHeads, numTails = runTrial(numFlips)
            ratios.append(numHeads/float(numTails))
            diffs.append(abs(numHeads - numTails))
        meanRatios.append(sum(ratios)/numTrials)
        meanDiffs.append(sum(diffs)/numTrials)
        ratiosSds.append(stdDev(ratios))
        diffsSds.append(stdDev(diffs))
    pylab.plot(xAxis, meanRatios, 'bo')
    pylab.title('Mean Heads/Tails Ratios ('+ str(numTrials) + ' Trials)')
    pylab.xlabel('Number of Flips')
    pylab.ylabel('Mean Heads/Tails')
    pylab.semilogx()
    pylab.figure()
    pylab.plot(xAxis, ratiosSds, 'bo')
    pylab.title('SD Heads/Tails Ratios ('+ str(numTrials) + ' Trials)')
    pylab.xlabel('Number of Flips')
    pylab.ylabel('Standard Deviation')
    pylab.semilogx()
    pylab.semilogy()
    pylab.figure()
    pylab.title('Mean abs(#Heads - #Tails) ('+ str(numTrials) + 'Trials)')
    pylab.xlabel('Number of Flips')
    pylab.ylabel('Mean abs(#Heads - #Tails)')
    pylab.plot(xAxis, meanDiffs, 'bo')
    pylab.semilogx()
    pylab.semilogy()
    pylab.figure()
    pylab.plot(xAxis, diffsSds, 'bo')
    pylab.title('SD abs(#Heads - #Tails) (' + str(numTrials) + 'Trials)')
    pylab.xlabel('Number of Flips')
    pylab.ylabel('Standard Deviation')
    pylab.semilogx()
    pylab.semilogy()
#flipPlot(4, 20, 20)
#pylab.show()
print stdDev([11, 12, 13])
print stdDev([0, 1, 2, 3, 4, 5, 6])
print stdDev([1, 1, 1, 1, 1, 1, 1])
print stdDev([0, 0, 0, 3, 6, 6, 6])
print stdDev([6, 7, 5, 10])

def stdDevOfLengths(L):
    """
    L: a list of strings

    returns: float, the standard deviation of the lengths of the strings,
      or NaN if L is empty.
    """
    lst = []
    for word in L:
        lst.append(len(word))
    mean = sum(lst)/float(len(lst))
    tot = 0.0
    for num in lst:
        tot += (num - mean)**2
    return (tot/len(lst))**0.5

# using listcomps
def stdDevOfLengths2(L):
    n = float(len(L))
    if (n == 0):
        return float('NaN')
    lengths    = [len(s) for s in L]
    mean       = sum(lengths) / n
    squaredDev = [(l-mean)**2 for l in lengths]
    variance   = sum(squaredDev) / n
    return variance**(.5)

print stdDevOfLengths(['apples', 'oranges', 'kiwis', 'pineapples'])

def CV(X):
    mean = sum(X)/float(len(X))
    try:
        return stdDev(X)/mean
    except ZeroDivisionError:
        return float('NaN')

print round(CV([10, 4, 12, 15, 20, 5]), 4)
