class Clock(object):
    def __init__(self, time):
        self.time = time
    def print_time(self):
        time = '6:30'
        print self.time
boston_clock = Clock('5:30')
paris_clock = boston_clock
paris_clock.time = '7:30'
print boston_clock