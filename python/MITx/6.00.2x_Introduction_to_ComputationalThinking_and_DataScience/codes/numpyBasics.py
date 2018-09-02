import numpy
import numpy as np
cvalues = [25.6, 22.3, 12.6]
c = np.array(cvalues)
print c
print type(c)
fvalues = [x*9/5+32 for x in cvalues]
print fvalues, type(fvalues)
fvaluesNumpy = c*9/5+32
print fvaluesNumpy, type(fvaluesNumpy)