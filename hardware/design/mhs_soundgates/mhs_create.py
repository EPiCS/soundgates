'''
Created on Jan 19, 2014

@author: CaiusC
'''

#!/usr/bin/python

import sys, getopt, os
import shutil

from models import TGF

inputfile = ''
outputfile = ''

def main():
    # Check arguments
    if(len(sys.argv) == 0):
        usage()
        sys.exit(1)
    try:
        opts, args = getopt.getopt(sys.argv[1:],'hi:c:b',["ifile=","cfile=","base_design="])
    except getopt.GetoptError:
        usage()
        sys.exit(2)
    base_design = None
    component_path = None
    for opt, arg in opts:
        if opt == '-h':
            usage()
            sys.exit()
        elif opt in ("-i", "--ifile"):
            inputfile = arg
        elif opt in ("-c", "--cfile"):
            component_path = arg
            if not component_path.endswith('/'):
                component_path = component_path + '/'
        elif opt in ("-b", "--base_design"):
            base_design = arg
        else:
            assert False, "unhandled option"
    # Search for HW components
    clean()
    if base_design is None:
        # Base Design was not set by argument. Trying to find it.
        #base_design = os.getenv('SOUNDGATES')
        #base_design = os.path.join(base_design,'hardware/design/reference/zynq_audio_adau1761')
        base_design = "zynq_audio_adau1761"
        print "Info: Found Base Design in: ", base_design
    print 'Info: Using component path:', component_path
    tgf = TGF(inputfile, component_path)
    # Create setup_zynq file
    tgf.createSetupFile(base_design)
    # Link components into project folder
    tgf.linkComponents()
    # Execute ReconosScript
    checkReconOs()
    tgf.reconosSetup()
    
def clean():
    print "Info: Cleaning project folder"
    if (os.path.exists("project")):
        shutil.rmtree('project/')
        
def checkReconOs():
    reconos = os.getenv("RECONOS")
    if reconos is None:
        print 'Error: Can not build project. ReconoOS Variable is not set.'
        sys.exit()


def usage():
    print 'This script creates a mhs file out of the TGF'
    print 'Usage: mhs_create.py -i <patch.tgf> [-c <path-to-component-implementations>] [-b <name-of-the-base-design>]'
    

if __name__ == "__main__":
    #main(sys.argv[1:])#
    main()