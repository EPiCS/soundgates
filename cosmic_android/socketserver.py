#!/usr/bin/env python
import socket
m_localip='127.0.0.1' # Adresse des eigenen Rechners
m_port=5005
 
sock = socket.socket( socket.AF_INET,  socket.SOCK_DGRAM ) #internet,udp
sock.bind( (m_localip,m_port) )   

print '----- Socket Server -----'
print 'IP: ', m_localip
print 'Port: ', m_port
 
try:
	while True:
		data, addr = sock.recvfrom( 1024 ) #buffer size is 1024
		print "receiving message: ", data
        print "from address:", addr
finally:
	sock.close
