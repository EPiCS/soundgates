'''
Created on Jan 19, 2014

@author: CaiusC
'''

#!/usr/bin/python

import sys, getopt

from models import TGF

inputfile = ''
outputfile = ''

def main(argv):
    # Check arguments
    if(len(argv) == 0):
        usage()
        sys.exit(1)
    try:
        opts, args = getopt.getopt(argv,"hi:ob:",["ifile=","ofile=","base_design="])
    except getopt.GetoptError:
        usage()
        sys.exit(2)
    component_path = "components"
    base_design = "zynq_audio_adau1761"
    for opt, arg in opts:
        if opt == '-h':
            usage()
            sys.exit()
        elif opt in ("-i", "--ifile"):
            inputfile = arg
        elif opt in ("-c", "--cfile"):
            component_path = arg
        elif opt in ("-b", "--base_design"):
            base_design = arg
    # Search for HW components
    tgf = TGF(inputfile, component_path)
    # Create setup_zynq file
    tgf.createSetupFile(base_design)
    # Link components into project folder
    tgf.linkComponents()
    # Execute ReconosScript
    tgf.reconosSetup()
    

def usage():
    print 'This script creates a mhs file out of the TGF'
    print 'Usage: test.py -i <patch.tgf> [-c <path-to-component-implementations>] [-b <name-of-the-base-design>]'
    

if __name__ == "__main__":
    main(sys.argv[1:])