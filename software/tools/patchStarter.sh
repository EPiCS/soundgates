#!/bin/bash

SCRIPTPATH=$(readlink -f "$0")
PROJPATH=$(dirname "$SCRIPTPATH")
TGFPATH=/tmp/soundgates

# extract zip file to working directory
rm -r -f $TGFPATH 
mkdir $TGFPATH
cp $1 $TGFPATH
cd $TGFPATH
unzip *.zip
TGF=`ls *.tgf`

export LD_LIBRARY_PATH="$PROJPATH/lib"
cd $PROJPATH
$PROJPATH/Synthesizer.elf --patch-file=$TGFPATH/$TGF

