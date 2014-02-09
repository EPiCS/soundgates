#!/bin/bash

# no longer needed, should reside in the same folder
#PROJPATH=$1
PROJPATH=$(dirname $0)

# extract zip file to working directory
rm -r -f /tmp/soundgates 
mkdir /tmp/soundgates
cp $1 /tmp/soundgates
cd /tmp/soundgates
unzip *.zip
TGF=`ls *.tgf`

export LD_LIBRARY_PATH="$PROJPATH/lib"
$PROJPATH/Synthesizer.elf --patch-file=./$TGF --alsadevice=plughw:1,0

#export LD_LIBRARY_PATH="/home/gwue/git/synthesizer/pg-soundgates/software/zynq/Libraries/x86_64/lib"
#/home/gwue/git/synthesizer/pg-soundgates/software/zynq/Synthesizer/Release/Synthesizer.elf --soundcomponents=/home/gwue/git/synthesizer/pg-soundgates/software/zynq/SoundComponents/result/ --patch-file=./$TGF --alsadevice=plughw:1,0
