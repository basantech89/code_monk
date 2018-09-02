tech = ['MIT', 'Cal Tech']
ivys = ['Harvard', 'Yale', 'Brown']
univs = [tech, ivys]
univs1 = [['MIT', 'Cal Tech'], ['Harvard', 'Yale', 'Brown']]
tech.append('RPI')
for e in univs:
    print 'Univs contain'
    print e
    print 'Which contains'
    for u in e:
        print '    '+u
tech.sort()
tech.index('MIT')
tech.insert(0, 100)
tech.remove('Cal Tech')
tech.extend([1,2,3,4,5])
tech.pop(4) #4 is an index
tech.reverse()
tech.count('MIT')