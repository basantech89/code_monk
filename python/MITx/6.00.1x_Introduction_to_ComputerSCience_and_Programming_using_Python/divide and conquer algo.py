def isPalindrome(s):
    
    def toChars(s):
        s = s.lower()
        ans = ""
        for p in s:
            if p in 'abcdefghijklmnopqrstuvwxyz':
                ans += p
        return ans
    
    def isPal(s):
        if len(s)<=1:
            return True
        else:
            return s[0]==s[-1] and isPal(s[1:-1])
    return isPal(toChars(s))
print isPalindrome('Able was I ere I saw Elba')
print isPalindrome('Are we not drawn onward, we few, drawn onward to new era?')                                                                          