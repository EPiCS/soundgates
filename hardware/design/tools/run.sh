#!/bin/bash
source $1

while [ $# -gt 0 ]
do                   
  shift
done

cd ${RECONOS_REPO}tools/environment
source setup_reconos_toolchain.sh

#Compile device tree
#mkdir -p ${WORKING_DIR}devicetree
for file in $INPUT_DEVICE_TREE_INCLUDES
do
    echo "copying dts include: ${file}"
    cp $file ${WORKING_DIR}devicetree
done
echo "copying dts: ${INPUT_DEVICE_TREE}"
#cp $INPUT_DEVICE_TREE ${WORKING_DIR}devicetree/device_tree.tmp # copy the whole folder, the new .dts includes some other file there
cp -r $INPUT_DEVICE_TREE_FOLDER ${WORKING_DIR}
DTFOLDER_NAME=`basename $INPUT_DEVICE_TREE_FOLDER`


DT_TO_REPLACE='^\s*bootargs'
DT_REPLACEMENT="		bootargs = \"console=ttyPS0,${MINICOM_BAUDRATE} root=/dev/nfs rw nfsroot=${NFS_ROOT_IP}:${NFS_EXPORT},tcp ip=${ZEDBOARD_IP}::${ZEDBOARD_GATEWAY}:${ZEDBOARD_NETMASK}:zynq:eth0:off earlyprintk\";"

#for file in ${WORKING_DIR}devicetree/*
for file in ${WORKING_DIR}/${DTFOLDER_NAME}/*
do
    sed -i "/${DT_TO_REPLACE}/c\ ${DT_REPLACEMENT}" $file
done

echo "compiling devicetree"
$EXECUTABLE_DTC -I dts -O dtb -o $DEVICE_TREE_BLOB ${WORKING_DIR}/${DTFOLDER_NAME}/*.dts

echo "Loading initial bitstream"
echo "  FPGA Bitstream:             $INPUT_INITIAL_BITSTREAM"
echo -e "fpga -f ${INPUT_INITIAL_BITSTREAM}" | xmd

echo "Booting Linux on Zynq ..."
echo "  Linux image (uImage):       $INPUT_UIMAGE"
echo "  Device-Tree-Blob:           $DEVICE_TREE_BLOB"
echo "  Init-Skript (ps7_init.tcl): $INPUT_INIT"
echo "  U-Boot executable:          $INPUT_UBOOT"

echo -e "connect arm hw\nrst\nsource ${INPUT_INIT}\nps7_init\nps7_post_config\ndow -data ${DEVICE_TREE_BLOB} 0x02a00000\ndow -data ${INPUT_UIMAGE} 0x03000000\ndow ${INPUT_UBOOT}\nrun" | xmd

x-terminal-emulator -e "minicom -D ${MINICOM_DEVICE} -b ${MINICOM_BAUDRATE}"&
read -p "Press any key when linux has booted... " -n1 -s

echo "Loading patch bitstream"
echo -e "fpga -f $INPUT_BITSTREAM" | xmd

sshpass -p $LINUX_PASSWORD ssh $LINUX_USER@$ZEDBOARD_IP

