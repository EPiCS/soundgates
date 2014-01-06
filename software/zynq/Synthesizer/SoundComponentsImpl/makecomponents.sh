#!/bin/bash

exit

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