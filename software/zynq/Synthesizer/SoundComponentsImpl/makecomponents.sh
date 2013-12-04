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
