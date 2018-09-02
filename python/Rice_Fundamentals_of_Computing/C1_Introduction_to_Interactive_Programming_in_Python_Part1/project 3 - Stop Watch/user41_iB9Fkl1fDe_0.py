# template for "Stopwatch: The Game"
import simplegui

# define global variables
t, flag = 0.0, False

# define helper function format that converts time
# in tenths of seconds into formatted string A:BC.D
def format(t):
    BC = str(round((t % 60), 1))
    indi = BC.index('.')
    if len(BC[:indi]) == 1 : BC = '0' + BC
    return str(int(t // 60)) + ':' + BC 
    
# define event handlers for buttons; "Start", "Stop", "Reset"
def start():
    global flag
    flag = True
    tick()
    
def stop():
    global flag
    flag = False
    
def reset():
    global t, flag
    flag = False
    t = 0.0

# define event handler for timer with 0.1 sec interval
def tick():
    if flag:
        global t
        t += 0.1
        return format(t)

# define draw handler
def draw(canvas):
    canvas.draw_text(format(t), [100, 100], 30, \
                     'White', 'monospace')
    
# create frame
frame = simplegui.create_frame('Stopwatch', 300, 200)

# register event handlers
timer = simplegui.create_timer(100, tick)
frame.set_draw_handler(draw)
frame.add_button('Start', start, 100)
frame.add_button('Stop', stop, 100)
frame.add_button('Reset', reset, 100)

# start frame
frame.start()
timer.start()
# Please remember to review the grading rubric
