# implementation of card game - Memory

try:
	import simplegui
except ImportError:
	import SimpleGUICS2Pygame.simpleguics2pygame as simplegui
import random

# helper function to initialize globals
def new_game():
    global state, deck, expose, exposed_cards
    state = 0
    deck = [i for i in range(0, 8)] + [i for i in range(0, 8)]
    random.shuffle(deck)
    expose = [False for i in range(0, 16)]
    exposed_cards = []
    label.set_text("Turns 0")
    
# define event handlers
def mouseclick(pos):
    # add game state logic here
    global state, exposed_cards
    if expose[pos[0] // 50] == False:
        if state == 0:
            state = 1
        elif state == 1:
            state = 2
            label.set_text("Turns "+ str(int(label.get_text()[5:]) + 1))
        else :
            state = 1
            if deck[exposed_cards[-1]] != deck[exposed_cards[-2]]:
                expose[exposed_cards[-1]] = False
                expose[exposed_cards[-2]] = False
            exposed_cards.pop()
            exposed_cards.pop()		            
        exposed_cards.append(pos[0] // 50)
        expose[pos[0] // 50] = True

# cards are logically 50x100 pixels in size    
def draw(canvas):
    width = 0
    for i in range(len(deck)):
        if expose[i] == False:
            canvas.draw_polygon([(width, 0), (width+50, 0), (width+50, 100), (width, 100)], 2, 'Yellow', 'Green')
        else:
            canvas.draw_polygon([(width, 0), (width+50, 0), (width+50, 100), (width, 100)], 2, 'Yellow', 'Black')
            canvas.draw_text(str(deck[i]), ((2 * width + 50) / 2, 40), 50, 'White')
        width += 50

# create frame and add a button and labels
frame = simplegui.create_frame("Memory", 800, 100)
frame.add_button("Reset", new_game)
label = frame.add_label("Turns = 0")

# register event handlers
frame.set_mouseclick_handler(mouseclick)
frame.set_draw_handler(draw)

# get things rolling
new_game()
frame.start()

# Always remember to review the grading rubric
