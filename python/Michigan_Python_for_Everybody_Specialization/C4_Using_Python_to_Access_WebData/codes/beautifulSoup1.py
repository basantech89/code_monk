import urllib
from bs4 import BeautifulSoup
url = raw_input('Enter - ')
html = urllib.urlopen(url).read()
soup = BeautifulSoup(html)
tags = soup('span')
sum = 0
count = 0
for tag in tags:
    count += 1
    lst = list(tag)
    sum += int(lst[0])
print 'Sum ',sum
print 'Count ',count
