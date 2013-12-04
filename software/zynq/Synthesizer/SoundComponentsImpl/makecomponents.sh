#!/bin/bash

cd output
make clean
make all
cd ..

cd sine
make clean
make all
cd ..
