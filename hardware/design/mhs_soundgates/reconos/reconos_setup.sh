#!/bin/sh
#
#                                                        ____  _____
#                            ________  _________  ____  / __ \/ ___/
#                           / ___/ _ \/ ___/ __ \/ __ \/ / / /\__ \
#                          / /  /  __/ /__/ /_/ / / / / /_/ /___/ /
#                         /_/   \___/\___/\____/_/ /_/\____//____/
# 
# ======================================================================
# 
#   project:      ReconOS
#   author:       Christoph R��thing, University of Paderborn
#   description:  The setup script to generate a complete EDK-Project
#                 from a base design, ther ReconOS components and the
#                 users HWTs.
#   Arguments:     1: Base design folder  - required
#                  2: Reconos repository  - required
#                  3: #Hardware thread    - required
#                  4: Hardware threads to use - requred   
#                  5: Output directory    - optional   
# ======================================================================


# Check the required arguments
if [ -z "$1" -o  -z "$2" -o -z "$3" -z "$4" ]
then
	exit
fi

BASE_DESIGN=$1
#RECONOS=$2
NUM_HWTS=$2
HWTS=$3
OUTPUT_DIR=../project

# Check output folder
#if [ -n "$6" ]
#then
#    OUTPUT_DIR=$6
#fi

#if [ -d ${OUTPUT_DIR} ]
#then
#    echo "Output folder already exist"
#    exit
#fi

# Create output folder
#mkdir -p ${OUTPUT_DIR}

echo "Copying base design..."

cp -r ${BASE_DESIGN} ${OUTPUT_DIR}

DESIGN_NAME=`basename ${BASE_DESIGN}`

#if [ ! -d ${OUTPUT_DIR}/${DESIGN_NAME}/pcores ]
#then
#    mkdir ${OUTPUT_DIR}/${DESIGN_NAME}/pcores
#fi

echo "Copying reconos hardware"
cp -r ${RECONOS}/pcores/* ${OUTPUT_DIR}/${DESIGN_NAME}/pcores

echo "Generating ReconOS system ..."

# generate all files
find ${OUTPUT_DIR}/${DESIGN_NAME}/pcores -type f -exec ./reconos_preproc.sh {} $NUM_HWTS {} \;

# backup original file
mv ${OUTPUT_DIR}/${DESIGN_NAME}/system.mhs ${OUTPUT_DIR}/${DESIGN_NAME}/system.mhs.orig

# create mhs file	
python mhsaddhwts.py zynq ${OUTPUT_DIR}/${DESIGN_NAME}/system.mhs.orig ${NUM_HWTS} 0 $HWTS > ${OUTPUT_DIR}/${DESIGN_NAME}/system.mhs	

