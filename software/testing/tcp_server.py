import socket
import numpy
ip='127.0.0.1' # Adresse des eigenen Rechners
port=1338
BUFFER_SIZE = 32

s = socket.socket( socket.AF_INET, socket.SOCK_STREAM )
s.bind( ( ip, port) )
s.listen(1) 

conn, addr = s.accept()

print 'Waiting for a connection'
while 1:
    data = conn.recv(BUFFER_SIZE)
    if not data: break
    int_chars = [ord(c) for c in data]
    print int_chars
conn.close()
