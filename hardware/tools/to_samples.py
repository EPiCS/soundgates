#!/usr/bin/env python
# coding: utf8

import sys;
import struct;

def do_convert(filename):
    fid_in  = open(filename, 'rb')
    fid_out = open('sound_conv.out','w')
    data = fid_in.read(4)  # read 4 bytes = 32 Bit Sample  
    
    while data:
        ser = str(struct.unpack('<i', data)[0]) + '\n'
        fid_out.write(ser)
        data = fid_in.read(4)
    
    fid_in.close()
    fid_out.close()

if __name__ == "__main__":
    print "Converting..."

    do_convert(sys.argv[1])
    print "done"
