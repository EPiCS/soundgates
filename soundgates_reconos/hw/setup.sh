#!/bin/bash

BASE_DESIGN="base_design_ml605"
#BASE_DESIGN="ml605_linux_14.2"
HWTS="musicgenerator_v1_00_a"
EDKDIR="edk"

if [ -z "$RECONOS" ]
then
	echo "ERROR: RECONOS environment variable is not set. $RECONOS must point to the root of the ReconOS repository."
	exit 1
fi

echo "------------------------------------------------ WARNING ------------------------------------------------"
echo "-------------------- BUG: You have to make the i2s ports external inside of XPS -------------------------"
echo "------------------------------------------------ WARNING ------------------------------------------------"

if [ -d "$EDKDIR" ]; then
  # Control will enter here if $EDKDIR exists.
  echo "Removing EDK folder ..."
  rm -r edk/
fi

# copy base design
cp -r ../$BASE_DESIGN edk

# link to ReconOS pcores
mkdir edk/pcores
ln -s $RECONOS/pcores/* edk/pcores

# add links to hardware threads
cd edk/pcores
for HWT in $HWTS
do
	ln -sf ../../$HWT .
done
cd -

# add ReconOS to the edk project
cp edk/system.mhs edk/system.mhs.orig
$RECONOS/tools/mhsaddhwts.py edk/system.mhs.orig $HWTS > edk/system.mhs


