print "Please think of a number between 0 and 100!"
high = 100.0
low = 0.0
ans = (low+high)/2.0
h = "h"
l = "l"
c = "c" 
while 0 < high-low <=100:
    print "Is your secret number "+str(ans)+"?"
    y = raw_input("Enter 'h' to indicate the guess is too high. Enter 'l' to indicate the guess is too low. Enter'c' to indicate I guessed correctly: ")
    if y == h:    
        high = ans
        ans = int((low+high)/2.0)
    elif y == l:
        low = ans
        ans = int((low+high)/2.0)
    elif y == c:
        break
    else:
        print "Sorry, I did not understand your input."               
print "Game over. Your secret number was "+str(ans)
	