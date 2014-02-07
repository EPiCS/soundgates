import socket
from struct import *
ip='127.0.0.1' # Adresse des eigenen Rechners
port=1338

# ------------------
# Create Socket 
# ------------------
e_udp_sock = socket.socket( socket.AF_INET,  socket.SOCK_DGRAM )
e_udp_sock.bind( (ip,port) ) 

print '########Waiting for connection########'    
print 'Connection:'
print 'IP: ',ip
print 'Port: ',port

# ------------------
# TCP Server
# ------------------

def receive():
    while 1:
        data, addr = e_udp_sock.recvfrom( 5 ) # Receiving 4 Bytes
        #pcm_int = unpack('i', data)
        #print pcm_int
        pcm_int = unpack_from('i', data,0)
        index   = unpack_from('c', data,4)
        print 'index: ',ord( index[0] ),' pcm: ', pcm_int

receive()
