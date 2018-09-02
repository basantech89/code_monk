# Implementation of classic arcade game Pong

import simplegui
import random

# initialize globals - pos and vel encode vertical info for paddles
WIDTH = 600
HEIGHT = 400       
BALL_RADIUS = 20
PAD_WIDTH = 8
PAD_HEIGHT = 80
HALF_PAD_WIDTH = PAD_WIDTH / 2
HALF_PAD_HEIGHT = PAD_HEIGHT / 2
LEFT = False
RIGHT = True
ball_pos, ball_vel = [WIDTH/2, HEIGHT/2], [1, 1]
paddle1_vel, paddle2_vel = 0, 0
paddle1_pos, paddle2_pos = HEIGHT/2, HEIGHT/2
score1, score2 = 0, 0

# initialize ball_pos and ball_vel for new bal in middle of table
# if direction is RIGHT, the ball's velocity is upper right, else upper left
def spawn_ball(direction):
    global ball_pos, ball_vel # these are vectors stored as lists
    ball_pos, ball_vel = [WIDTH/2, HEIGHT/2], [1, 1]
    if not direction : ball_vel[0] = -ball_vel[0]
    
# define event handlers
def new_game():
    global paddle1_pos, paddle2_pos, paddle1_vel, paddle2_vel  # these are numbers
    global score1, score2  # these are ints
    paddle1_pos, paddle2_pos = HEIGHT/2, HEIGHT/2
    score1, score2 = 0, 0
    spawn_ball(RIGHT)
    
def draw(canvas):
    global score1, score2, paddle1_pos, paddle2_pos, ball_pos, ball_vel
    # draw mid line and gutters
    canvas.draw_line([WIDTH / 2, 0],[WIDTH / 2, HEIGHT], 1, "White")
    canvas.draw_line([PAD_WIDTH, 0],[PAD_WIDTH, HEIGHT], 1, "White")
    canvas.draw_line([WIDTH - PAD_WIDTH, 0],[WIDTH - PAD_WIDTH, HEIGHT], 1, "White")
    
    # update ball
    if ball_pos[1] - BALL_RADIUS <= 0 or ball_pos[1] + BALL_RADIUS >= HEIGHT:
        ball_vel[1] = -ball_vel[1]
    ball_pos[0] += ball_vel[0]
    ball_pos[1] -= ball_vel[1]
    # draw ball
    canvas.draw_circle(ball_pos, BALL_RADIUS, 1, 'White', 'White')
    
    # update paddle's vertical position, keep paddle on the screen
    paddle1_pos += paddle1_vel
    paddle2_pos += paddle2_vel
    # draw paddles
    upY1, downY1 = paddle1_pos - HALF_PAD_HEIGHT, paddle1_pos + HALF_PAD_HEIGHT
    upY2, downY2 = paddle2_pos - HALF_PAD_HEIGHT, paddle2_pos + HALF_PAD_HEIGHT
    if upY1 < 0:
        upY1 = 0
        downY1 = PAD_HEIGHT
    if upY2 < 0:
        upY2 = 0
        downY2 = PAD_HEIGHT
    if downY1 > HEIGHT:
        downY1 = HEIGHT
        upY1 = HEIGHT - PAD_HEIGHT
    if downY2 > HEIGHT:
        downY2 = HEIGHT
        upY2 = HEIGHT - PAD_HEIGHT
    point_list1 = [[0, upY1], [PAD_WIDTH, upY1], [PAD_WIDTH, downY1], [0, downY1]]
    point_list2 = [[WIDTH-PAD_WIDTH, upY2], [WIDTH, upY2], [WIDTH, downY2], [WIDTH-PAD_WIDTH, downY2]]
    canvas.draw_polygon(point_list1, 1, 'White', 'White')
    canvas.draw_polygon(point_list2, 1, 'White', 'White')
    
    # determine whether paddle and ball collide
    if ball_pos[0] + BALL_RADIUS >= WIDTH - PAD_WIDTH and (paddle2_pos - (PAD_HEIGHT/2) <= ball_pos[1] + BALL_RADIUS \
                                                           and ball_pos[1] - BALL_RADIUS <= paddle2_pos + (PAD_HEIGHT/2)):
        ball_vel[0] = -ball_vel[0]
        ball_vel[0] += -1
    elif (ball_pos[0] - BALL_RADIUS) <= PAD_WIDTH and (paddle1_pos - (PAD_HEIGHT/2) <= ball_pos[1] + BALL_RADIUS and \
                                                       ball_pos[1] - BALL_RADIUS <= paddle1_pos + (PAD_HEIGHT/2)):
        ball_vel[0] = -ball_vel[0]
        ball_vel[0] += 1
    elif ball_pos[0] + BALL_RADIUS >= WIDTH - PAD_WIDTH and not (paddle2_pos - (PAD_HEIGHT/2) <= ball_pos[1] + BALL_RADIUS and \
                                                                 ball_pos[1] - BALL_RADIUS <= paddle2_pos + (PAD_HEIGHT/2)):
        score1 += 1
        spawn_ball(LEFT)
    elif ball_pos[0] - BALL_RADIUS <= PAD_WIDTH and not (paddle1_pos - (PAD_HEIGHT/2) <= ball_pos[1] + BALL_RADIUS and \
                                                         ball_pos[1] - BALL_RADIUS <= paddle1_pos + (PAD_HEIGHT/2)):
        score2 += 1
        spawn_ball(RIGHT)
            
    # draw scores
    canvas.draw_text(str(score1), [3*WIDTH/8, HEIGHT/4], 40, 'White', 'monospace')
    canvas.draw_text(str(score2), [5*WIDTH/8, HEIGHT/4], 40, 'White', 'monospace')    
        
def keydown(key):
    global paddle1_vel, paddle2_vel
    if key == simplegui.KEY_MAP['w'] : paddle1_vel -= 4
    if key == simplegui.KEY_MAP['up'] : paddle2_vel -= 4
    if key == simplegui.KEY_MAP['s'] : paddle1_vel += 4
    if key == simplegui.KEY_MAP['down'] : paddle2_vel += 4        
        
def keyup(key):
    global paddle1_vel, paddle2_vel
    paddle1_vel, paddle2_vel = 0, 0
    
def reset():
    new_game()

# create frame
frame = simplegui.create_frame("Pong", WIDTH, HEIGHT)
frame.set_draw_handler(draw)
frame.set_keydown_handler(keydown)
frame.set_keyup_handler(keyup)
frame.add_button('Reset', reset, 100)

# start frame
new_game()
frame.start()
