#!/bin/bash

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

