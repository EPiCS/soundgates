#!/bin/bash

#exit

#LD_LIBRARY_PATH auf default setzen damit die crosslibs auf dem PG Rechner nicht dazwischenfunken
LD_LIBRARY_PATH_OLD=$LD_LIBRARY_PATH
LD_LIBRARY_PATH=/usr/local/lib/:/usr/local/lib:/usr/lib64/:/usr/local/lib64
export LD_LIBRARY_PATH

cd output
make clean
make all
cd ..

cd sine
make clean
make all
cd ..

cd add
make clean
make all
cd ..

cd delay
make clean
make all
cd ..

cd const
make clean
make all
cd ..

cd lowpass
make clean
make all
cd ..

cd noise
make clean
make all
cd ..

cd sampleplayer
make clean
make all
cd ..

cd mixer
make clean
make all
cd ..

cd dummysink
make clean
make all
cd ..

LD_LIBRARY_PATH=$LD_LIBRARY_PATH_OLD
export LD_LIBRARY_PATH
