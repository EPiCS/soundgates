#!/usr/bin/env python

# date: 07/02/14
# name: CaiusC
# description: 
# Reads data from a Soundgates Debugging component via UDP

import socket
import sys
#import thread
from threading import Thread
import Queue 
import pylab as plt
import numpy as np
from struct import *
IP='127.0.0.1'
PORT=1338


class DebuggingServer(object):
    MAX_INDEX = 63
    
    def __init__(self, ip, port):
        self.Ip = ip
        self.Port = port
        self.Socket = socket.socket( socket.AF_INET,  socket.SOCK_DGRAM )
        self.Queue = Queue.Queue()

    def start(self):
        # Initialize Server
        print np.version.version
        print 'Waiting for connection'
        print ' IP:\t', self.Ip
        print ' Port:\t', self.Port
        # Start Server in thread
        self.Socket.bind( (self.Ip,self.Port) )
        t_server = Thread(target = self.receive)
        t_server.daemon = True
        t_server.start()
        # Start plotter in main loop
        self.plot()


    def receive(self):
        sample_arr = []
        while 1:
            # Receive 5 Bytes
            data, addr = self.Socket.recvfrom( 5 )
            pcm_int = unpack_from('i', data,0)
            received_index = unpack_from('c', data,4)
            received_index = ord(received_index[0])
            sample_arr.insert( received_index+1, pcm_int )
            if(received_index == self.MAX_INDEX):
                arr = np.asanyarray(sample_arr, dtype=np.int32)
                self.Queue.put(np.asanyarray(arr))
                
    def plot(self):
        # animate plot
        f = plt.figure()
        plt.ion()
        f.show()
        while 1:
            data = self.Queue.get()
            f.clear()
            plt.plot(data)
            plt.draw()
            self.Queue.task_done()
            
#     def plot(self):
#         # animate plot
#         plt.ion()
#         # initialize plot
#         ydata = [0] * 64
#         ax1 = plt.axes()
#         line, = plt.plot(ydata)
#         # set axis label
#         plt.xlabel('time')
#         plt.ylabel('amplitude')
# 
#         while 1:
#             data = self.Queue.get()
#             print data
#             ydata.append(data)
#             print ydata
#             line.set_xdata(np.arange(len(ydata)))
#             line.set_ydata(ydata)  # update the data
#             plt.draw() # update the plot
#             self.Queue.task_done()

if __name__ == "__main__":
    srv = DebuggingServer(IP, PORT)
    srv.start()