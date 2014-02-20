#!/usr/bin/python

from liblo import *
import time
import random
import socket
import thread
import time

def m(mid):
	return mid - 21

frequencies = [27.5]

target = Address('192.168.1.56',50050) # OSC Target (where the synthesizer runs)

TCP_IP = '192.168.1.56'  # TCP Handshake (this computers IP here)
TCP_PORT = 50052
BUFFER_SIZE = 1024

for i in range(1,88):
	frequencies.append( frequencies[i-1] * 1.059463094 )# 1.059463094 = 12th root of 2

blackkeys = [ m(61), m(63), m(66), m(68), m(70)]

boogie = [ m(60), m(64), m(67), m(69), m(70), m(69), m(67), m(64) ]

key = 0
offset = 0


s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((TCP_IP,TCP_PORT))
s.listen(1)

conn, addr = s.accept()

data = conn.recv(BUFFER_SIZE)
print "received data:", data
conn.send("/speed f [0.1:0.4]||/noteoffset i [0:12]")
conn.close()


speedbitch = 0
shakes = 0
nextNoteOffset = 0
noteOffset = 0


class LibLoServer(ServerThread):
	def __init__(self):
		ServerThread.__init__(self,TCP_PORT)

	@make_method('/speed', 'f')
	def foo_bar_callback(self, path, args):
#		print "received ",path," -- ",args
		global shakes
		shakes += 1#args[0]

	@make_method('/noteoffset', 'i')
	def cracknutte(self, path, args):
#		print "received ",path," -- ",args
		global nextNoteOffset
		nextNoteOffset = args[0]

try:
	server = LibLoServer()
except ServerError, err:
	print str(err)
	sys.exit()

server.start()


def calcspeed():
	global speedbitch
	global shakes
	last = time.time()
	while True:
		time.sleep(0.25)
		print "Shakes collected: ",shakes
		shakes /= 1.8
		print "Shakes after: ",shakes
		if shakes > 60:	
			shakes = 60
		speedbitch = 0.1 + 0.9 / (shakes+1)
		print "Speed: ",speedbitch


thread.start_new_thread(calcspeed, ())

speedbitch = 1

# loop and dispatch messages every 100ms
while True:
	print "Speed: ",speedbitch, "Offset: ",noteOffset
	if (key == 0):
		noteOffset = nextNoteOffset

	send(target, "/Frequency", frequencies[boogie[key] + (noteOffset)])
	key += 1
	key = key % len(boogie)
	send(target, "/keytrigger", 1)
	time.sleep(speedbitch)
#	time.sleep(0.5/random.randint(1,4))
	send(target, "/keytrigger", 0)
	time.sleep(speedbitch)
#	time.sleep(0.25/random.randint(1,2))

