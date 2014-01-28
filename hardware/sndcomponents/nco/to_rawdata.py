#!/usr/bin/env python
# -*- coding: ascii -*-

"""
package.module
~~~~~~~~~~~~~

A description which can be long and explain the complete
functionality of this module even with indented code examples.
Class/Function however should not be documented here.

:copyright: year by my name, see AUTHORS for more details
:license: license_name, see LICENSE for more details
"""


import struct
import sys

def do_convert(filename):
    """ """
    try:
        f_in  = open(filename, 'r')
        f_out = open('raw_audio.out', 'wb')

        sample = 0

        for line in f_in:
	    try:
            	sample = int(line)
            	data = struct.pack("i", sample) # pack integer in a binary string
            	f_out.write(data)
	    except:
		print "Cannot convert: " + line

    finally:
        f_in.close()
        f_out.close()
        


if __name__=='__main__':
    print "Converting..."

    do_convert(sys.argv[1])
    print "done"
