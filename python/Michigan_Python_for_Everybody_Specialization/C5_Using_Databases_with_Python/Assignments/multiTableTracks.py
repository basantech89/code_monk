import xml.etree.ElementTree as ET
import sqlite3

conn = sqlite3.connect('trackdb.sqlite')
cur = conn.cursor()

# Make some tables
cur.execute('''
CREATE TABLE IF NOT EXISTS Artist(
                    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
                    name TEXT UNIQUE
)''')
cur.execute('''
CREATE TABLE IF NOT EXISTS Album(
                    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
                    artist_id INTEGER,
                    title TEXT UNIQUE
)''')
cur.execute('''
CREATE TABLE IF NOT EXISTS Track(
                    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
                    album_id INTEGER,
                    title TEXT UNIQUE,
                    len INTEGER, rating INTEGER, count INTEGER
)''')


fname = raw_input('Enter file name: ')
if len(fname) < 1 : fname = 'Library.xml'

#<key>Track ID</key><integer>369</integer>

def lookup(d, key):
    found = False
    for child in d:
        if found : return child.text
        if child.tag == 'key' and child.text == key : found = True
    return None

stuff = ET.parse(fname)
all = stuff.findall('dict/dict/dict')
print 'Dict count: ', len(all)
for entry in all:
    if lookup(entry, 'Track ID') is None : continue
    name = lookup(entry, 'Name')
    artist = lookup(entry, 'Artist')
    album = lookup(entry, 'Album')
    count = lookup(entry, 'Play Count')
    rating = lookup(entry, 'Rating')
    length = lookup(entry, 'Total Time')
    if name is None or artist is None or album is None : continue
    print name, artist, album, count, rating, length

    cur.execute('''INSERT OR IGNORE INTO Artist (name) VALUES (?)''', (artist, ))
    cur.execute('SELECT id FROM Artist WHERE name = ?', (artist, ))
    artist_id = cur.fetchone()[0]

    cur.execute('''INSERT OR IGNORE INTO Album (title, artist_id) VALUES (?, ?)
                    ''', (album, artist_id))
    cur.execute('SELECT id FROM Album WHERE title = ?', (album, ))
    album_id = cur.fetchone()[0]

    cur.execute('''INSERT OR REPLACE INTO Track
                    (title, album_id, len, rating, count)
                    VALUES (?, ?, ?, ?, ?)''',
                    (name, album_id, length, rating, count))

    conn.commit()
