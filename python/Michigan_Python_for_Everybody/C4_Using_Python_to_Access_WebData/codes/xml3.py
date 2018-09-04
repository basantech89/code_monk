import xml.etree.ElementTree as ET
tree = ET.parse('user.xml')
root = tree.getroot()
print root.tag, root.attrib
print tree.find('country')
print 'yo',tree.findall('.')
print tree.findall('./country/neighbor')
print root.attrib
print root[0][3].attrib
for n in root.iter('rank'):
    print n.text
print root.find('rank')
print root.findall(".//year/..[@name='/Singapore']")
