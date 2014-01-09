#!/bin/bash

cd output
make clean
make all_arm
cd ..

cd sine
make clean
make all_arm
cd ..

cd add
make clean
make all_arm
cd ..

cd delay
make clean
make all_arm
cd ..

cd const
make clean
make all_arm
cd ..

cd lowpass
make clean
make all_arm
cd ..

cd noise
make clean
make all_arm
cd ..