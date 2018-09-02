import numpy
import numpy as np
#zero dimensional array
x = np.array(42)
print "zero dimensional array"
print x, type(x), np.ndim(x), x.shape
#one dimensional array
f = np.array([1,2,3,4])
v = np.array([1.3,2.3,3.6,5.9])
print "one dimensional array"
print f, type(f), np.ndim(f), f.dtype, f.shape
print v, type(v), np.ndim(v), v.dtype, np.shape(v)
#two and multidimensional arrays
#arrays are of arbitrary dimension, we create them by passing nested lists or tuples to the
#arrya method of numpy
a = np.array([[3.4,-1.1,6.3],[9.2,-5.3,-1.6],[1,6.9,3]])
print "two dimensioanl array"
print a, type(a), np.ndim(a), a.dtype, a.shape
b = np.array([[[1.0 ,2.3 ,5.9], [3.2, 6.5, 1.9]],[[6.9, 9.8, 2], [1, 6, 3]], [[1, 2, 3], [6, 3, 2]]])
print "three dimensional array"
print b, type(b), np.ndim(b), b.dtype, b.shape