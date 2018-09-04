import sqlite3

#making a connection object
conn = sqlite3.connect("emaildb.sqlite")
#cur variable has the cursor object in it through which you can send commands
#itls like the command or row that's coming back
cur = conn.cursor()

cur.execute('''DROP TABLE IF EXISTS Counts''')
cur.execute('''CREATE TABLE Counts (org TEXT, count INTEGER)''')

fname = 'mbox.txt'
#fname = raw_input('Enter file name: ')
#if (len(fname) < 1) : fname = 'mbox-short.txt'
fh = open(fname)
for line in fh:
    if not line.startswith('From: ') : continue
    pieces = line.split('@')
    email = pieces[1].rstrip()
    cur.execute('SELECT count FROM Counts WHERE org = ?', (email, ))
    try:
        count = cur.fetchone()[0]
        cur.execute('UPDATE Counts SET count = count + 1 WHERE org = ?', (email, ))
    except:
        cur.execute('''INSERT INTO Counts (org, count) VALUES (?, 1)''', (email, ))
    # This statement commits outstanding changes to disk each
    # time through the loop - the program can be made faster
    # by moving the commit so it runs only after the loop completes
    conn.commit()

sqlstr = 'SELECT org, count FROM Counts ORDER BY count DESC LIMIT 10'

for row in cur.execute(sqlstr):
    #we are converting email to string in case if any unknown utf-8 character is present
    print str(row[0]), row[1]

cur.close()
