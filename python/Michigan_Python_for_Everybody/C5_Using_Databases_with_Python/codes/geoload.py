import urllib
import sqlite3
import json
import time

serviceurl = 'http://maps.googleapis.com/maps/api/geocode/json?'

conn = sqlite3.connect('geodata.sqlite')
cur = conn.cursor()

cur.execute('''
create table if not exists Locations (address text, geodata text)''')

fh = open('where.data')
count = 0
for line in fh:
    if count > 200 : break
    address = line.strip()
    print ''
    cur.execute("select geodata from Locations where address = ?", (buffer(address), ))
    #buffer is a way to convert(force) address if it happens to be unicode
    try:
        data = cur.fetchone()[0]
        print 'Found in database ', address
        continue
    except:
        pass
    print 'Resolving ', address
    url = serviceurl + urllib.urlencode({'sensor':'false', 'address':address})
    print 'Retrieving ', url
    uh = urllib.urlopen(url)
    data = uh.read()
    print 'Retrieved', len(data), 'characters', data[:20].replace('\n', ' ')
    count += 1
    try:
        js = json.loads(str(data))
        #converting to string if unicode causes an error
    except:
        continue
    if 'status' not in js or (js['status'] != 'OK' and js['status'] != 'ZERO_RESULTS'):
        print '==== Failure To Retrieve ===='
        print data
        break
    cur.execute('''insert into Locations (address, geodata) values (?, ?)''',
                    (buffer(address), buffer(data)))
    conn.commit()
    time.sleep(1)
