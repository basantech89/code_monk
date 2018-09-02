def divison(x, y):
    try:
        result = x/y
    except ZeroDivisionError, e:
        print 'Divison by zero ',str(e)
    except TypeError, e:
        print 'Wrong type ',str(e)
        divison(int(x), int(y))        
    else:
        print 'result is ',result
    finally:
        print 'executing finally clause ',type(x)
divison('2', '0')                         