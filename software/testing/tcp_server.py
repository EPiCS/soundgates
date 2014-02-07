import socket
from struct import *
ip='127.0.0.1' # Adresse des eigenen Rechners
port=1338
BUFFER_SIZE = 4 * 64

s = socket.socket( socket.AF_INET, socket.SOCK_STREAM )
s.bind( ( ip, port) )
s.listen(1)

try: 
    while True: 
        conn, addr = s.accept() 
        while True: 
            data = conn.recv(BUFFER_SIZE)

            if not data: 
                conn.close() 
                break
            for i in range(64): 
                pcm_int = unpack_from('i', data, i*4)
                print pcm_int
            #print "[%s] %s" % (addr[0], data) 

finally: 
    s.close()
    
#pcm_int = unpack('i', data)
#print pcm_int

