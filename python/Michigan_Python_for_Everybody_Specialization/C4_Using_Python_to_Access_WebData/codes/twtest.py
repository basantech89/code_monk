import urllib
from twurl import augment

print '* Calling Twitter...'
#augment the url
url = augment('https://api.twitter.com/1.1/statuses/user_timeline.json',
        {'screen_name': 'johnsmi45027987', 'count': '2'})
print url
connection = urllib.urlopen(url)
data = connection.read()#body(getting back json)
print data
#asking for headers in dictionary form(getting back json)
headers = connection.info().dict
print headers
