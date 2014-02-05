DEVICE_TREE_BLOB=zynq-zed.dtb
INPUT_UIMAGE=../../linux/arch/arm/boot/uImage
INPUT_INIT=./SDK/SDK_Export/hw/ps7_init.tcl
#INPUT_INIT=./SDK/SDK_Workspace/hw/ps7_init.tcl
INPUT_UBOOT=../../u-boot-xlnx/u-boot


echo -e "fpga -f ./implementation/system.bit" | xmd

#echo -e "fpga -f ./SDK/SDK_Workspace/hw/system.bit" | xmd


echo -e "connect arm hw\nrst\nsource ${INPUT_INIT}\nps7_init\nps7_post_config\ndow -data ${DEVICE_TREE_BLOB} 0x02a00000\ndow -data ${INPUT_UIMAGE} 0x03000000\ndow ${INPUT_UBOOT}\nrun" | xmd

