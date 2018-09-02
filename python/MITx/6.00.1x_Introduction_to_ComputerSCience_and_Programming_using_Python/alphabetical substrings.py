s = 'abcdefbjbjljm jmkghijklmnomnlknmkbk'
a2z = 'abcdefghijklmnopqrstuvwxyz'
def yoyo(s,a2z):
    for a in range(0,len(s)):
        for b in range(len(s),0,-1):
            for c in range(0,len(a2z)):
                for d in range(len(a2z),0,-1):
                    if s[a:b] == a2z[c:d] and abs(b-a) == abs(d-c):
                        return s[a:b]                        
print yoyo(s,a2z)                        