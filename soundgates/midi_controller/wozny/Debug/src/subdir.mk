################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../src/testperiph.c \
../src/xgpio_tapp_example.c \
../src/xintc_tapp_example.c \
../src/xtmrctr_intr_example.c \
../src/xtmrctr_selftest_example.c \
../src/xuartlite_selftest_example.c 

LD_SRCS += \
../src/lscript.ld 

OBJS += \
./src/testperiph.o \
./src/xgpio_tapp_example.o \
./src/xintc_tapp_example.o \
./src/xtmrctr_intr_example.o \
./src/xtmrctr_selftest_example.o \
./src/xuartlite_selftest_example.o 

C_DEPS += \
./src/testperiph.d \
./src/xgpio_tapp_example.d \
./src/xintc_tapp_example.d \
./src/xtmrctr_intr_example.d \
./src/xtmrctr_selftest_example.d \
./src/xuartlite_selftest_example.d 


# Each subdirectory must supply rules for building sources it contributes
src/%.o: ../src/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: MicroBlaze gcc compiler'
	mb-gcc -Wall -O0 -g3 -c -fmessage-length=0 -Wl,--no-relax -I../../wozny_bsp/microblaze_0/include -mxl-barrel-shift -mxl-pattern-compare -mcpu=v8.50.a -mno-xl-soft-mul -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


