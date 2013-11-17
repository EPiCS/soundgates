################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../src/Samplebuffer.c \
../src/Samplebuffer_getset.c \
../src/SynthesizerTest.c \
../src/osc_handler.c 

OBJS += \
./src/Samplebuffer.o \
./src/Samplebuffer_getset.o \
./src/SynthesizerTest.o \
./src/osc_handler.o 

C_DEPS += \
./src/Samplebuffer.d \
./src/Samplebuffer_getset.d \
./src/SynthesizerTest.d \
./src/osc_handler.d 


# Each subdirectory must supply rules for building sources it contributes
src/%.o: ../src/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: ARM Linux gcc compiler'
	arm-xilinx-linux-gnueabi-gcc -Wall -O0 -g3 -I/home/soundgates/zynq/reconos_zynq/lib/include -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


