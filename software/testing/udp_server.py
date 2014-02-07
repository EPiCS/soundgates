import socket
import numpy
from struct import *
ip='127.0.0.1' # Adresse des eigenen Rechners
port=1338
 
e_udp_sock = socket.socket( socket.AF_INET,  socket.SOCK_DGRAM )
e_udp_sock.bind( (ip,port) ) 
print '########Waiting for connection########'    
print 'Connection:'
print 'IP: ',ip
print 'Port: ',port

def receive():
    while 1:
        data, addr = e_udp_sock.recvfrom( 4 ) # Receiving 4 Bytes
        pcm_int = unpack('i', data)
        print pcm_int
        #hex_chars = map(hex,map(ord,data))
        #hex_string = "".join(c[2:4] for c in hex_chars)
        #print hex_string + ","
        #int_chars = [ord(c) for c in data]
        #print int_chars

receive()
