# template for "Guess the number" mini-project
# input will come from buttons and an input field
# all output for the game will be printed in the console

import simplegui
import random

# initialize global variables used in your code here
num_range = 100
num_remaining = 7
random_num = random.randint(0, 100)

# helper function to start and restart the game
def new_game():
    # Starting a new game 
    global num_range, num_remaining
    print "New game. Range is from 0 to %d \
    \nNumber of remaining guesses is %d" % (num_range, num_remaining)
    print ""
    
# define event handlers for control panel
def range100():
    # button that changes the range to [0,100) and starts a new game 
    
    global num_range, num_remaining, random_num
    num_range, num_remaining = 100, 7
    random_num = random.randint(0, num_range)
    new_game()

def range1000():
    # button that changes the range to [0,1000) and starts a new game     
    
    global num_range, num_remaining
    num_range, num_remaining = 1000, 10
    random_num = random.randint(0, num_range)
    new_game()
    
def input_guess(guess):
    # main game logic goes here	
    
    global num_range, num_remaining, random_num
    guess = int(guess)
    print 'Guess was ', guess
    num_remaining -= 1
    print 'Number of remaining guesses is ', num_remaining
    if guess == random_num:
        print "Correct!\n"
        return range100() if num_range == 100 else range1000()       
    if num_remaining == 0:
        print "You ran out of guesses. The number was ", random_num
        print ""
        return range100() if num_range == 100 else range1000()
    if guess < random_num:
        print "Higher!\n"
    if guess > random_num:
        print "Lower!\n"

    
# create frame
frame = simplegui.create_frame("Guess the number", 200, 200)

# register event handlers for control elements and start frame
frame.add_button("Range is [0, 100)", range100, 200)
frame.add_button("Rang is [0, 1000)", range1000, 200)
frame.add_input("Enter a guess", input_guess, 200)

# call new_game 
new_game()

# Start handlers for created frame
frame.start()
# always remember to check your completed program against the grading rubric
