import json
import sqlite3

conn = sqlite3.connect('rosterdb.sqlite')
cur = conn.cursor()

#DO some setup
cur.executescript('''
drop table if exists User;
drop table if exists Member;
drop table if exists Course;

create table User (
    id integer not null primary key autoincrement unique,
    name text unique
);

create table Course (
    id integer not null primary key autoincrement unique,
    title text unique
);

create table Member (
    user_id integer,
    course_id integer,
    role integer,
    primary key (user_id, course_id)
)
''')


fname = raw_input('Enter file name: ')
if len(fname) < 1 : fname = 'roster_data.json'

#[
#['Charley', 'si110', 1]
#]

str_data = open(fname).read()
json_data = json.loads(str_data)

for entry in json_data:
    name = entry[0]
    title = entry[1]
    role = entry[2]
    print name, title

    cur.execute('''insert or ignore into User (name) values (?)''', (name, ))
    cur.execute('select id from User where name = ?', (name, ))
    user_id = cur.fetchone()[0]

    cur.execute('''insert or ignore into Course (title) values (?)''', (title, ))
    cur.execute('select id from Course where title = ?', (title, ))
    course_id = cur.fetchone()[0]

    cur.execute('''insert or replace into Member (user_id, course_id, role) values
                    (?, ?, ?)''', (user_id, course_id, role))

    conn.commit()
