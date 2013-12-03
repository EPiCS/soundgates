#!/bin/bash

#Files etc...
SOUNDGATES_EDK=/home/soundgates/git/reconos_zynq/demos/soundgates/edk_zynq_linux/
RECONOS_REPO=/home/soundgates/git/reconos_zynq/
SOUNDGATES_REPO=/home/soundgates/git/pg-soundgates/
ZEDBOARD_REPO=/home/soundgates/git/zedboard/
ZYNC_REPO=/home/soundgates/zynq/
RECONOS_REPO_TOOLS=${RECONOS_REPO}tools/
WORKING_DIR=/tmp/

#ZedBoard communication stuff
MINICOM_DEVICE=/dev/ttyACM0
MINICOM_BAUDRATE=115200
LINUX_USER=root
LINUX_PASSWORD=root
ZEDBOARD_IP=192.168.1.99
ZEDBOARD_NETMASK=255.255.255.0
ZEDBOARD_GATEWAY=192.168.1.1
NFS_ROOT_IP=192.168.1.100
NFS_EXPORT=/exports/rootfs_archlinux

#Executables
EXECUTABLE_DTC=${ZEDBOARD_REPO}linux/scripts/dtc/dtc

#Input Files
#INPUT_DEVICE_TREE=${SOUNDGATES_REPO}hardware/design/reference/zynq_audio_adau1761/zynq-zed.dts #should work
INPUT_DEVICE_TREE=${RECONOS_REPO}cache/os_boot/zynq-zed_v2.dts
INPUT_UIMAGE=${ZEDBOARD_REPO}linux/arch/arm/boot/uImage
INPUT_INIT=${RECONOS_REPO}cache/os_boot/ps7_init.tcl
INPUT_UBOOT=${ZYNC_REPO}u-boot-xlnx/u-boot
#INPUT_INITIAL_BITSTREAM=${SOUNDGATES_REPO}hardware/design/reference/zynq_audio_adau1761/implementation/download.bit #should work
INPUT_INITIAL_BITSTREAM=${RECONOS_REPO}demos/soundgates/edk_zynq_linux/implementation/download.bit # works
INPUT_BITSTREAM=${RECONOS_REPO}demos/soundgates/edk_zynq_linux/implementation/download.bit

#Intermediate Files
DEVICE_TREE_BLOB=${WORKING_DIR}devicetree.dtb

#--no changes required--

cd ${RECONOS_REPO}tools/environment
source setup_reconos_toolchain.sh

#Compile device tree
cp $INPUT_DEVICE_TREE ${WORKING_DIR}device_tree.tmp

sed -i "/bootargs/c\		bootargs = \"console=ttyPS0,${MINICOM_BAUDRATE} root=/dev/nfs rw nfsroot=${NFS_ROOT_IP}:${NFS_EXPORT},tcp ip=${ZEDBOARD_IP}::${ZEDBOARD_GATEWAY}:${ZEDBOARD_NETMASK}:zynq:eth0:off earlyprintk\";" ${WORKING_DIR}device_tree.tmp

$EXECUTABLE_DTC -O dtb -o $DEVICE_TREE_BLOB ${WORKING_DIR}device_tree.tmp

# is this the only dtb that works? where is the source file
DEVICE_TREE_BLOB=/home/soundgates/git/reconos_zynq/cache/os_boot/devicetree.dtb

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

