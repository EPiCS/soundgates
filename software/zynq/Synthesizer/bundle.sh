#!/bin/bash

DEST=$1
ARCH=$2
mkdir -p $DEST

rsync -u -r -l ../Libraries/$2/lib $DEST
rsync -u -r ../SoundComponents/result $DEST/components
rsync -u -r Release/Synthesizer.elf $DEST
rsync -u -r Release/config $DEST 
