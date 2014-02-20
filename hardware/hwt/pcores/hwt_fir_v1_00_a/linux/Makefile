XILINX_BIN_PATH=/opt/Xilinx/14.6/ISE_DS/EDK/gnu/arm/lin/bin
LIB_PATH=../../../../../software/zynq/Libraries/arm


CC_ARM=$(XILINX_BIN_PATH)/arm-xilinx-linux-gnueabi-g++

NAME=FIR

all: 
	$(CC_ARM) fir_coeff_lp_.cpp test.cpp -Wall -Wno-unused-variable -DZYNQ -fPIC -isystem"$(LIB_PATH)/include" -L"$(LIB_PATH)/lib" -lreconos -lpthread -o test_$(NAME).elf
	@cp test_$(NAME).elf /exports/rootfs_archlinux/home/soundgates/test/
