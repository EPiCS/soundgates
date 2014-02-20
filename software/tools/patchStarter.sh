#!/bin/bash

SCRIPTPATH=$(readlink -f "$0")
PROJPATH=$(dirname "$SCRIPTPATH")
#TGFPATH=/tmp/soundgates
TGFPATH=$PROJPATH

# extract zip file to working directory
#rm -r -f $TGFPATH 
#mkdir $TGFPATH
#cp $1 $TGFPATH
cd $TGFPATH
rm *.tgf
unzip -u $1
TGF=`ls *.tgf`

export LD_LIBRARY_PATH="$PROJPATH/lib"
cd $PROJPATH
$PROJPATH/Synthesizer.elf --patch-file=$TGFPATH/$TGF
