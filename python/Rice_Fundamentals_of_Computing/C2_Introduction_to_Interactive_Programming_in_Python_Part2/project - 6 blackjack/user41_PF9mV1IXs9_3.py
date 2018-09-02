# Mini-project #6 - Blackjack

import simplegui
import random

# load card sprite - 936x384 - source: jfitz.com
CARD_SIZE = (72, 96)
CARD_CENTER = (36, 48)
card_images = simplegui.load_image("http://storage.googleapis.com/codeskulptor-assets/cards_jfitz.png")

CARD_BACK_SIZE = (72, 96)
CARD_BACK_CENTER = (36, 48)
card_back = simplegui.load_image("http://storage.googleapis.com/codeskulptor-assets/card_jfitz_back.png")    

# initialize some useful global variables
in_play = False
outcome = ""
score = 0

# define globals for cards
SUITS = ('C', 'S', 'H', 'D')
RANKS = ('A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K')
VALUES = {'A':1, '2':2, '3':3, '4':4, '5':5, '6':6, '7':7, '8':8, '9':9, 'T':10, 'J':10, 'Q':10, 'K':10}


# define card class
class Card:
    def __init__(self, suit, rank):
        if (suit in SUITS) and (rank in RANKS):
            self.suit = suit
            self.rank = rank
        else:
            self.suit = None
            self.rank = None
            print "Invalid card: ", suit, rank

    def __str__(self):
        return self.suit + self.rank

    def get_suit(self):
        return self.suit

    def get_rank(self):
        return self.rank

    def draw(self, canvas, pos):
        card_loc = (CARD_CENTER[0] + CARD_SIZE[0] * RANKS.index(self.rank), 
                    CARD_CENTER[1] + CARD_SIZE[1] * SUITS.index(self.suit))
        canvas.draw_image(card_images, card_loc, CARD_SIZE, [pos[0] + CARD_CENTER[0], pos[1] + CARD_CENTER[1]], CARD_SIZE)
        
# define hand class
class Hand:
    def __init__(self):
        self.hand = []	# create Hand object

    def __str__(self):
        # return a string representation of a hand
        s = "Hand contains "
        for card in self.hand : s += str(card) + " "
        return s
    
    def add_card(self, card):
        if card not in self.hand : self.hand.append(card)	# add a card object to a hand

    def get_value(self):
        # count aces as 1, if the hand has an ace, then add 10 to hand value if it doesn't bust
        # compute the value of the hand, see Blackjack video
        global VALUES
        hand_val, lst = 0, []
        for card in self.hand:
            hand_val += VALUES[card.get_rank()]
            lst.append(card.get_rank())
        if 'A' not in lst : return hand_val
        else:
            if hand_val + 10 <= 21 : return hand_val + 10
            else : return hand_val
            
    def draw(self, canvas, pos):
        # draw a hand on the canvas, use the draw method for cards
        for card in self.hand:
            card.draw(canvas, pos)
            pos[0] += 106
        
# define deck class 
class Deck:
    def __init__(self):
        # create a Deck object
        self.deck = []
        global RANKS, SUITS
        for suit in SUITS:
            for rank in RANKS : self.deck.append(Card(suit, rank))
        
    def shuffle(self):
        # shuffle the deck
        random.shuffle(self.deck)

    def deal_card(self):
        # deal a card object from the deck
        card = random.choice(self.deck)
        self.deck.remove(card)
        return card
    
    def __str__(self):
        # return a string representing the deck
        s = "Deck contains "
        for card in self.deck : s += card.get_suit() + card.get_rank() + " "
        return s

#define event handlers for buttons
def deal():
    global outcome, score, in_play, player_hand, dealer_hand, deck
    if in_play:
        in_play = False
        score -= 1
    if not in_play:
        deck = Deck(); deck.shuffle()
        player_hand, dealer_hand = Hand(), Hand()
        for i in range(2):
            player_hand.add_card(deck.deal_card()); dealer_hand.add_card(deck.deal_card())
        in_play, outcome = True, "Hit or Stand?"

def hit():
    global player_hand, deck, in_play, score, outcome
    # if the hand is in play, hit the player
    if in_play:
        player_hand.add_card(deck.deal_card())
        if player_hand.get_value() > 21 :            
            in_play, outcome = False, "You went busted and lose"
            score -= 1           
    # if busted, assign a message to outcome, update in_play and score
       
def stand():
    global in_play, score, outcome, deck, dealer_hand, player_hand
    if in_play:
        while dealer_hand.get_value() <= player_hand.get_value():
            dealer_hand.add_card(deck.deal_card())
        if dealer_hand.get_value() > 21:
            outcome, in_play = "Dealer busted, You win", False
            score += 1
        else:
            in_play = False
            if dealer_hand.get_value() < player_hand.get_value():
                outcome = "You win"
                score += 1
            else:
                outcome = "You lose"
                score -= 1
    # if hand is in play, repeatedly hit dealer until his hand has value 17 or more

    # assign a message to outcome, update in_play and score

# draw handler    
def draw(canvas):
    # test to make sure that card.draw works, replace with your code below
    global player_hand, dealer_hand, in_play, outcome, score
    canvas.draw_text('Blackjack', (80, 70), 40, 'Yellow', 'monospace')
    canvas.draw_text('Score ' + str(score), (400, 70), 30, 'Yellow', 'monospace')
    canvas.draw_text('Dealer', (70, 130), 30, 'Black', 'sans-serif')
    if in_play:
        canvas.draw_image(card_back, CARD_CENTER, CARD_SIZE, [106, 200], CARD_SIZE)
        dealer_hand.hand[1].draw(canvas, [180, 152])
        canvas.draw_text(outcome, (250, 300), 30, 'Black', 'sans-serif')
    else:
        dealer_hand.draw(canvas, [70, 152])
        canvas.draw_text(outcome, (250, 130), 30, 'Black', 'sans-serif')
        canvas.draw_text('New deal?', (250, 300), 30, 'Black', 'sans-serif')
    canvas.draw_text('Player', (70, 300), 30, 'Black', 'sans-serif')
    player_hand.draw(canvas, [70, 350])

# initialization frame
frame = simplegui.create_frame("Blackjack", 600, 600)
frame.set_canvas_background("Green")

#create buttons and canvas callback
frame.add_button("Deal", deal, 200)
frame.add_button("Hit",  hit, 200)
frame.add_button("Stand", stand, 200)
frame.set_draw_handler(draw)


# get things rolling
deal()
frame.start()


# remember to review the gradic rubric