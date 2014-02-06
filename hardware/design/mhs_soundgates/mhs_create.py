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
reconos = ''
soundgates = ''

def main():
    checkEnvironmentalVariables()
    ###################
    # Check arguments #
    ###################
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
    clean()
    if base_design is None:
        # Base Design was not set by argument. Setting it to default value.
        base_design = "cf_adv7511_zed"
        print 'Info: I am trying to use the base design ', base_design
    # Search for HW components
    tgf = TGF(inputfile, component_path, base_design)
    # Create setup_zynq file
    tgf.createSetupFile()
    # Link components into project folder
    tgf.linkComponents()
    # Execute ReconosScript
    tgf.reconosSetup()
    
def clean():
    print "Info: Cleaning project folder"
    if (os.path.exists("project")):
        shutil.rmtree('project/')
        
def checkEnvironmentalVariables():
    checkEnvReconOs()
    checkEnvSoundgates()
        
def checkEnvReconOs():
    reconos = os.getenv("RECONOS")
    if not os.path.isdir(reconos):
        print 'Error: Can not build project.\nRECONOS Variable is not set or not pointing to a directory.'
        sys.exit()
        
def checkEnvSoundgates():
    soundgates = os.getenv("SOUNDGATES")
    if not os.path.isdir(soundgates):
        print 'Error: Can not build project.\nSOUNDGATES Variable is not set or not pointing to a directory.'
        sys.exit()    


def usage():
    print 'This script creates a mhs file out of the TGF'
    print 'Usage: mhs_create.py -i <patch.tgf> [-c <path-to-component-implementations>] [-b <name-of-the-base-design>]'
    

if __name__ == "__main__":
    #main(sys.argv[1:])#
    main()