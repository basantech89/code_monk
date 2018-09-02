# towers of hanoi
def printMove(fr, to):
	print "move from ", fr, " to ", to
def Towers(n, fr, to, spare):
	if n == 1:
		printMove(fr, to)
	else:
		Towers(n-1, fr, spare, to)
		Towers(1, fr, to, spare)
		Towers(n-1, spare, to, fr)
Towers(5, 'fr', 'to', 'spare')
				