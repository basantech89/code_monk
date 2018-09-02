# 6.00x Problem Set 6
#
# Part 1 - HAIL CAESAR!

import string
import random

WORDLIST_FILENAME = "E:\code_ProblemSet6\words.txt"

# -----------------------------------
# Helper code
# (you don't need to understand this helper code)
def loadWords():
    """
    Returns a list of valid words. Words are strings of lowercase letters.
    
    Depending on the size of the word list, this function may
    take a while to finish.
    """
    print "Loading word list from file..."
    inFile = open(WORDLIST_FILENAME, 'r')
    wordList = inFile.read().split()
    print "  ", len(wordList), "words loaded."
    return wordList

def isWord(wordList, word):
    """
    Determines if word is a valid word.

    wordList: list of words in the dictionary.
    word: a possible word.
    returns True if word is in wordList.

    Example:
    >>> isWord(wordList, 'bat') returns
    True
    >>> isWord(wordList, 'asdf') returns
    False
    """
    word = word.lower()
    word = word.strip(" !@#$%^&*()-_+={}[]|\\:;'<>?,./\"")
    return word in wordList

def randomWord(wordList):
    """
    Returns a random word.

    wordList: list of words  
    returns: a word from wordList at random
    """
    return random.choice(wordList)

def randomString(wordList, n):
    """
    Returns a string containing n random words from wordList

    wordList: list of words
    returns: a string of random words separated by spaces.
    """
    return " ".join([randomWord(wordList) for _ in range(n)])

def randomScrambled(wordList, n):
    """
    Generates a test string by generating an n-word random string
    and encrypting it with a sequence of random shifts.

    wordList: list of words
    n: number of random words to generate and scamble
    returns: a scrambled string of n random words

    NOTE:
    This function will ONLY work once you have completed your
    implementation of applyShifts!
    """
    s = randomString(wordList, n) + " "
    shifts = [(i, random.randint(0, 25)) for i in range(len(s)) if s[i-1] == ' ']
    return applyShift(s, shifts)[:-1]

def getStoryString():
    """
    Returns a story in encrypted text.
    """
    return open("E:\code_ProblemSet6\story.txt", "r").read()


# (end of helper code)
# -----------------------------------


#
# Problem 1: Encryption
#
def buildCoder(shift):
    """
    Returns a dict that can apply a Caesar cipher to a letter.
    The cipher is defined by the shift value. Ignores non-letter characters
    like punctuation, numbers and spaces.

    shift: 0 <= int < 26
    returns: dict
    """
    lalp = []
    ualp = []
    for char in string.ascii_lowercase:
        lalp.append(char)
    for char in string.ascii_uppercase:
        ualp.append(char)       
    coder = {}
    for char in lalp:
        coder.keys().append(char)
        coder[char] = lalp[shift]
        shift += 1
        if shift == 26:
            shift = 0
    for letter in ualp:        
        coder.keys().append(letter)
        coder[letter] = ualp[shift]    
        shift += 1
        if shift == 26:
            shift = 0
    return coder                                      
def applyCoder(text, coder):
    """
    Applies the coder to the text. Returns the encoded text.

    text: string
    coder: dict with mappings of characters to shifted characters
    returns: text after mapping coder chars to original text
    """
    cipher_text = ""
    for char in text:
        if char in coder.keys():
            cipher_text += coder[char]
        if char in string.punctuation or char in string.digits or char == ' ':
            cipher_text += char          
    return cipher_text
def applyShift(text, shift):
    """
    Given a text, returns a new text Caesar shifted by the given shift
    offset. Lower case letters should remain lower case, upper case
    letters should remain upper case, and all other punctuation should
    stay as it is.

    text: string to apply the shift to
    shift: amount to shift the text (0 <= int < 26)
    returns: text after being shifted by specified amount.
    """
    coder = buildCoder(shift)
    cipher_text = applyCoder(text, coder)
    return cipher_text
  
#
# Problem 2: Decryption
#
def findBestShift(wordList, text):
    """
    Finds a shift key that can decrypt the encoded text.

    text: string
    returns: 0 <= int < 26
    """
    #create integer variables to store max number of valid words and best shift
    #so far
    max_real_words = 0
    best_shift = 0
    for shift in range(0,26):
        #converting cipher text into plain text
        plain_text = applyShift(text, shift)
        #splitting every word of plain_text and adding them to a list                 
        list_plain_text = plain_text.split(' ')
        #create an integer variable to store total number of valid word for a list
        total_real_words = 0
        for word in list_plain_text:
            #checking if the word is a valid word
            if isWord(wordList, word) == True:
                total_real_words += 1
        #updating the status of variable for max valid words and for shift                
        if total_real_words > max_real_words:
            max_real_words = total_real_words
            best_shift = shift
    #returning the best shift            
    return best_shift            
                                                
def decryptStory():
    """
    Using the methods you created in this problem set,
    decrypt the story given by the function getStoryString().
    Use the functions getStoryString and loadWords to get the
    raw data you need.

    returns: string - story in plain text
    """
    new_wordList = loadWords()
    encrypted_story = getStoryString()
    best_shift = findBestShift(new_wordList, encrypted_story)
    decrypted_story = applyShift(encrypted_story, best_shift)   
    return decrypted_story
#
# Build data structures used for entire session and run encryption
#

if __name__ == '__main__':
    # To test findBestShift:
    wordList = loadWords()
    s = applyShift('Hello, world!', 8)
    bestShift = findBestShift(wordList, s)
    assert applyShift(s, bestShift) == 'Hello, world!'
    # To test decryptStory, comment the above four lines and uncomment this line:
    #    decryptStory()
print findBestShift(wordList, "YmJ yJfHmJw'x sfrj nx YfGnyMf?")
print findBestShift(wordList, 'Ftq cgul ue... TmDp!')
print findBestShift(wordList, 'De, rkj jxuhu Yi q JQ dqcut Qblyd!')
print findBestShift(wordList, 'Hello, world!')
print decryptStory()