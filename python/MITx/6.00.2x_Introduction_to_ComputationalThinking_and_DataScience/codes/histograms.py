import random
import pylab

vals = []
for i in range(100000):
    num = random.random()
    vals.append(num)
xmin, xmax = pylab.xlim()
ymin, ymax = pylab.ylim()
print 'x-range =', xmin, '-', xmax
print 'y-range =', ymin, '-', ymax
pylab.hist(vals, bins = 11)
pylab.show()
#pylab.xlim(-1.0, 2.0)
