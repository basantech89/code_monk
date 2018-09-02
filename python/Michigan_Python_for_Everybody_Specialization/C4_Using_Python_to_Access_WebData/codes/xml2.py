import xml.etree.ElementTree as ET
input = '''<stuff>
    <users>
        <user x="2">
            <id>001</id>
            <name>Chuck</name>
        </user>
        <user x="7">
            <id>009</id>
            <name>Brent</name>
        </user>
    </users>
</stuff>'''

tree = ET.fromstring(input)
lst = tree.findall('users/user')
'''print 'User count:', len(lst)
for item in lst:
    print 'Name',item.find('name').text
    print 'Id',item.find('id').text
    print 'Attribute',item.get('x')
'''
print tree.tag
print tree.findall('.')
print tree[0][0].attrib
