monthNumber = {'jan':1, 'feb':2, 'mar':3, 1:'jan', 2:'feb', 3:'mar'}
print monthNumber['mar']
monthNumber[4] = 'april'
print monthNumber.values()
print monthNumber.has_key(1)
print monthNumber.keys()
del monthNumber[1]
print monthNumber
print len(monthNumber.values())