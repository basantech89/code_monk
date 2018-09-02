import pylab
import random
import fittingGoodness

def getData(filename):
    dataFile = open(filename, 'r')
    distances = []
    masses = []
    discardHeader = dataFile.readline()
    for line in dataFile:
        d, m = line.split()
        distances.append(float(d))
        masses.append(float(m))
    dataFile.close()
    return (masses, distances)

def plotData(filename):
    xVals, yVals = getData(filename)
    xVals = pylab.array(xVals)
    yVals = pylab.array(yVals)
    xVals = xVals * 9.81#converting the mass to force (F = mg)
    pylab.plot(xVals, yVals, 'bo', label = 'Measured Displacement')
    pylab.title('Measured Displacement of Springs')
    pylab.xlabel('Force (Newtons)')
    pylab.ylabel('Distance (Meters)')

#plotData('springData.txt')
#pylab.show()


def testErrors(ntrials = 50000, npts = 100):
    results = [0] * ntrials
    for i in range(ntrials):
        s = 0 # sum of random points
        for j in xrange(npts):
            s += random.triangular(-1, 1) #probability density function
        results[i] = s
    #plot result in a histogram
    pylab.hist(results, bins = 50)
    pylab.title('Sum of 100 random points -- Triangular PDF (10,000 trials)')
    pylab.xlabel('Sum')
    pylab.ylabel('Number of Trials')

#testErrors()
#pylab.show()


def fitData(filename):
    xVals, yVals = getData(filename)
    xVals = pylab.array(xVals)
    yVals = pylab.array(yVals)
    xVals = xVals * 9.81#converting the mass to force (F = mg)
    pylab.plot(xVals, yVals, 'bo', label = 'Measured Displacement')
    pylab.title('Measured Displacement of Springs')
    pylab.xlabel('Force (Newtons)')
    pylab.ylabel('Distance (Meters)')
    # polyfit finds the values of the parameters for the predictin that minimize the
    # sum of the squares of the errors or SSE, also called least squares
    # pylab.polyfit(xVals, yVals, degree)
    a, b = pylab.polyfit(xVals, yVals, 1) # fit y = ax + b
    # use line equation to graph predicted values
    estYVals = a*xVals + b
    k = 1/a
    pylab.plot(xVals, estYVals, label = 'Linear fit, k = ' + str(round(k, 5)))
    pylab.legend(loc = 'best')

#fitData('springData.txt')
#pylab.show()

# Terman's Law - Cubic Relationship

def fitData1(filename):
    xVals, yVals = getData(filename)
    xVals = pylab.array(xVals)
    yVals = pylab.array(yVals)
    xVals = xVals * 9.81#converting the mass to force (F = mg)
    pylab.plot(xVals, yVals, 'bo', label = 'Measured Displacement')
    pylab.title('Measured Displacement of Springs')
    pylab.xlabel('Force (Newtons)')
    pylab.ylabel('Distance (Meters)')
    # polyfit finds the values of the parameters for the predictin that minimize the
    # sum of the squares of the errors or SSE, also called least squares
    # pylab.polyfit(xVals, yVals, degree)
    a, b = pylab.polyfit(xVals, yVals, 1) # fit y = ax + b
    # use line equation to graph predicted values
    estYVals = a*xVals + b
    pylab.plot(xVals, estYVals, label = 'Linear fit')
    a, b, c, d = pylab.polyfit(xVals, yVals, 3)
    estYVals = a*xVals**3 + b*xVals**2 + c*xVals + d
    pylab.plot(xVals, estYVals, label = 'cubic fit')
    pylab.legend(loc = 'best')

#fitData1('springData.txt')
#pylab.show()

def fitData2(filename):
    xVals, yVals = getData(filename)
    extX = pylab.array(xVals + [1.05, 1.1, 1.15, 1.2, 1.25])
    xVals = pylab.array(xVals)
    yVals = pylab.array(yVals)
    xVals = xVals * 9.81#converting the mass to force (F = mg)
    extX = extX * 9.81#converting the mass to force (F = mg)
    pylab.plot(xVals, yVals, 'bo', label = 'Measured Displacement')
    pylab.title('Measured Displacement of Springs')
    pylab.xlabel('Force (Newtons)')
    pylab.ylabel('Distance (Meters)')
    # polyfit finds the values of the parameters for the predictin that minimize the
    # sum of the squares of the errors or SSE, also called least squares
    # pylab.polyfit(xVals, yVals, degree)
    a, b = pylab.polyfit(xVals, yVals, 1) # fit y = ax + b
    # use line equation to graph predicted values
    estYVals = a*extX + b
    pylab.plot(extX, estYVals, label = 'Linear fit')
    a, b, c, d = pylab.polyfit(xVals, yVals, 3)
    estYVals = a*extX**3 + b*extX**2 + c*extX + d
    pylab.plot(extX, estYVals, label = 'cubic fit')
    pylab.legend(loc = 'best')

#fitData2('springData.txt')
#pylab.show()

def fitData3(filename):
    xVals, yVals = getData(filename)
    xVals = pylab.array(xVals[:-6])
    yVals = pylab.array(yVals[:-6])
    xVals = xVals * 9.81#converting the mass to force (F = mg)
    pylab.plot(xVals, yVals, 'bo', label = 'Measured Displacement')
    pylab.title('Measured Displacement of Springs')
    pylab.xlabel('Force (Newtons)')
    pylab.ylabel('Distance (Meters)')
    # polyfit finds the values of the parameters for the predictin that minimize the
    # sum of the squares of the errors or SSE, also called least squares
    # pylab.polyfit(xVals, yVals, degree)
    a, b = pylab.polyfit(xVals, yVals, 1) # fit y = ax + b
    # use line equation to graph predicted values
    estYVals = a*xVals + b
    k = 1/a
    pylab.plot(xVals, estYVals, label = 'Linear fit, k = ' + str(round(k, 5)))
    pylab.legend(loc = 'best')

#fitData3('springData.txt')
#pylab.show()


def modifiedFitData(filename):
    xVals, yVals = getData(filename)
    xVals = pylab.array(xVals)
    yVals = pylab.array(yVals)
    xVals = xVals * 9.81#converting the mass to force (F = mg)
    pylab.plot(xVals, yVals, 'bo', label = 'Measured Displacement')
    pylab.title('Measured Displacement of Springs')
    pylab.xlabel('Force (Newtons)')
    pylab.ylabel('Distance (Meters)')
    # polyfit finds the values of the parameters for the predictin that minimize the
    # sum of the squares of the errors or SSE, also called least squares
    # pylab.polyfit(xVals, yVals, degree)
    a, b = pylab.polyfit(xVals, yVals, 1) # fit y = ax + b
    # use line equation to graph predicted values
    estYVals = a*xVals + b
    k = 1/a
    pylab.plot(xVals, estYVals, label = 'Linear fit, k = ' + str(round(k, 5)) +
    ', R2 = ' + str(round(fittingGoodness.rSquare(yVals, estYVals), 4)))
    pylab.legend(loc = 'best')

#modifiedFitData('springData.txt')
#pylab.show()


def modifiedFitData3(filename):
    xVals, yVals = getData(filename)
    xVals = pylab.array(xVals[:-6])
    yVals = pylab.array(yVals[:-6])
    xVals = xVals * 9.81#converting the mass to force (F = mg)
    pylab.plot(xVals, yVals, 'bo', label = 'Measured Displacement')
    pylab.title('Measured Displacement of Springs')
    pylab.xlabel('Force (Newtons)')
    pylab.ylabel('Distance (Meters)')
    # polyfit finds the values of the parameters for the predictin that minimize the
    # sum of the squares of the errors or SSE, also called least squares
    # pylab.polyfit(xVals, yVals, degree)
    a, b = pylab.polyfit(xVals, yVals, 1) # fit y = ax + b
    # use line equation to graph predicted values
    estYVals = a*xVals + b
    k = 1/a
    pylab.plot(xVals, estYVals, label = 'Linear fit, k = ' + str(round(k, 5))
    + ', R2 = ' + str(round(fittingGoodness.rSquare(yVals, estYVals), 4)))
    pylab.legend(loc = 'best')

modifiedFitData3('springData.txt')
pylab.show()
