def foo(arg):
    x = 1
    y = 2
    print locals()
    locals()['x'] = 2
    print 'x:',x

foo(7)

z = 7
globals()['z'] = 8
print z


a = 5
def show():
    global a
    a += 6
    print a

show()
