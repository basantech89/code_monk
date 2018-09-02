# -*- coding: utf-8 -*-
import xml.etree.ElementTree as ET

#If the XML input has namespaces, tags and attributes with prefixes in the form prefix:sometag get
#expanded to {uri}sometag where the prefix is replaced by the full URI. Also, if there is a default
#namespace, that full URI gets prepended to all of the non-prefixed tags.
#Here is an XML example that incorporates two namespaces, one with the prefix “fictional” and the other
#serving as the default namespace:

xml_text = '''<?xml version="1.0"?>
<actors xmlns:fictional="http://characters.example.com"
        xmlns="http://people.example.com">
    <actor>
        <name>John Cleese</name>
        <fictional:character>Lancelot</fictional:character>
        <fictional:character>Archie Leach</fictional:character>
    </actor>
    <actor>
        <name>Eric Idle</name>
        <fictional:character>Sir Robin</fictional:character>
        <fictional:character>Gunther</fictional:character>
        <fictional:character>Commander Clement</fictional:character>
    </actor>
</actors>'''
root = ET.fromstring(xml_text)
for actor in root.findall('{http://people.example.com}actor'):
    name = actor.find('{http://people.example.com}name')
    print name.text
    for char in actor.findall('{http://characters.example.com}character'):
        print ' |-->', char.text

#A better way to search the namespaced XML example is to create a dictionary with your own prefixes
#and use those in the search functions:

ns = {'real_person': 'http://people.example.com',
      'role': 'http://characters.example.com'}

for actor in root.findall('real_person:actor', ns):
    name = actor.find('real_person:name', ns)
    print name.text
    for char in actor.findall('role:character', ns):
        print ' |-->', char.text
