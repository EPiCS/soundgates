################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../src/software_sound_components/dummy.c \
../src/software_sound_components/mixer.c \
../src/software_sound_components/wave_generator.c \
../src/software_sound_components/wavefileplayer.c 

OBJS += \
./src/software_sound_components/dummy.o \
./src/software_sound_components/mixer.o \
./src/software_sound_components/wave_generator.o \
./src/software_sound_components/wavefileplayer.o 

C_DEPS += \
./src/software_sound_components/dummy.d \
./src/software_sound_components/mixer.d \
./src/software_sound_components/wave_generator.d \
./src/software_sound_components/wavefileplayer.d 


# Each subdirectory must supply rules for building sources it contributes
src/software_sound_components/%.o: ../src/software_sound_components/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: ARM Linux gcc compiler'
	arm-xilinx-linux-gnueabi-gcc -Wall -O0 -g3 -I/home/soundgates/zynq/reconos_zynq/lib/include -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


