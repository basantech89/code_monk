import urllib
from bs4 import BeautifulSoup

url = raw_input('Enter URL: ')
count = int(raw_input('Enter Count: '))
position = int(raw_input('Enter Position: '))
print 'Retrieving: ',url
for i in range(count):
    html = urllib.urlopen(url).read()
    soup = BeautifulSoup(html)
    tags = soup('a')
    url = tags[position-1].get('href', None)
    if i <= count - 2:
        print 'Retrieving: ',url
    if i == count - 1:
        print 'Last Url:',url
#url = 'https://pr4e.dr-chuck.com/tsugi/mod/python-data/data/known_by_Fikret.html'
