import re

#reading a file and searching a string in it
'''hand = open('downloads/boo.txt')
print 'regular method'
for line in hand:
    line = line.rstrip()
    if line.find('hello') >= 0:
        print line
#using regular expression
hand = open('downloads/boo.txt')
print '\nusing regular expressions'
for line in hand:
    line = line.rstrip()
    if re.search('hello', line):
        print line'''
#lookling for a line that starts with that string using regular methods
'''hand = open('downloads/boo.txt')
print 'regular method'
for line in hand:
    line = line.rstrip()
    if line.startswith('hello'):
        print line
#using regular expression
hand = open('downloads/boo.txt')
print '\nusing regular expressions'
for line in hand:
    line = line.rstrip()
    if re.search('^hello', line):
        print line'''
#^X.*:
#^X-\S+:
x = 'my 2 favourite numbers are 19 and 42'
y = re.findall('[0-9]+', x)
print y
a = 'From: using the character:'
#greedy matching
y = re.findall('^F.+:', a)
print y
z = re.findall('^F.+?:', a)
print z
b = 'From rocks@gmail.com hey boo'
w = re.findall('^From (\S+@\S+)', b)
print w
v = re.findall('^From .*@([^ ]*)', b)
print v
u = 'X-kfbsjk: 6.64654153'
t = re.findall('X-.+?: ([0-9].[0-9]+)', u)
print t
