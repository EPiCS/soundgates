################################################################################
# Automatically-generated file. Do not edit!
################################################################################

-include ../makefile.init

RM := rm -rf

# All of the sources participating in the build are defined here
-include sources.mk
-include src/subdir.mk
-include subdir.mk
-include objects.mk

ifneq ($(MAKECMDGOALS),clean)
ifneq ($(strip $(C_DEPS)),)
-include $(C_DEPS)
endif
ifneq ($(strip $(S_UPPER_DEPS)),)
-include $(S_UPPER_DEPS)
endif
endif

-include ../makefile.defs

# Add inputs and outputs from these tool invocations to the build variables 
ELFSIZE += \
soundgates_sw.elf.size \

ELFCHECK += \
soundgates_sw.elf.elfcheck \


# All Target
all: soundgates_sw.elf secondary-outputs

# Tool invocations
soundgates_sw.elf: $(OBJS) ../src/lscript.ld $(USER_OBJS)
	@echo 'Building target: $@'
	@echo 'Invoking: MicroBlaze gcc linker'
	mb-gcc -Wl,--no-relax -Wl,-T -Wl,../src/lscript.ld -L../../soundgates_sw_bsp/microblaze_0/lib -mxl-barrel-shift -mxl-pattern-compare -mcpu=v8.50.a -mno-xl-soft-mul -o "soundgates_sw.elf" $(OBJS) $(USER_OBJS) $(LIBS)
	@echo 'Finished building target: $@'
	@echo ' '

soundgates_sw.elf.size: soundgates_sw.elf
	@echo 'Invoking: MicroBlaze Print Size'
	mb-size soundgates_sw.elf  |tee "soundgates_sw.elf.size"
	@echo 'Finished building: $@'
	@echo ' '

soundgates_sw.elf.elfcheck: soundgates_sw.elf
	@echo 'Invoking: Xilinx ELF Check'
	elfcheck soundgates_sw.elf -hw ../../soundgates_hw_platform/system.xml -pe microblaze_0  |tee "soundgates_sw.elf.elfcheck"
	@echo 'Finished building: $@'
	@echo ' '

# Other Targets
clean:
	-$(RM) $(OBJS)$(C_DEPS)$(EXECUTABLES)$(ELFSIZE)$(ELFCHECK)$(S_UPPER_DEPS) soundgates_sw.elf
	-@echo ' '

secondary-outputs: $(ELFSIZE) $(ELFCHECK)

.PHONY: all clean dependents
.SECONDARY:

-include ../makefile.targets
