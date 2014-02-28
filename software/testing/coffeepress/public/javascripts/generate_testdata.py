#!/usr/bin/python
import math
import sys

maxint = sys.maxint
minint = -sys.maxint - 1
step = 20

print '['
for x in range(0, 100):
	if x < 99:
		sys.stdout.write( str( math.sin(x) ) + ', ' )
	else:
		sys.stdout.write( str( math.sin(x) ))
print ']'