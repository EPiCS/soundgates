import socket
import numpy
ip='127.0.0.1' # Adresse des eigenen Rechners
port=1338
 
e_udp_sock = socket.socket( socket.AF_INET,  socket.SOCK_DGRAM )
e_udp_sock.bind( (ip,port) ) 
print '########Waiting for connection########'    
print 'Connection:'
print 'IP: ',ip
print 'Port: ',port


def toInt(arr):
    res = 0
    res += (int(arr[0]))
    res += (int(arr[1]) << 8*1)
    res += (int(arr[2]) << 8*2)
    res += (int(arr[3]) << 8*3)
    return res

def receive():
    while 1:
        data, addr = e_udp_sock.recvfrom( 1 ) # Receiving 4 Bytes
        #hex_chars = map(hex,map(ord,data))
        #hex_string = "".join(c[2:4] for c in hex_chars)
        #print hex_string + ","
        int_chars = [ord(c) for c in data]
        print int_chars

receive()
