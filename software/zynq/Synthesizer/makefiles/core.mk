#Build shared core library

# Paths
XILINX_DIR=/opt/Xilinx/14.6/ISE_DS/EDK/gnu/arm/lin/bin
CORE_SRC_DIR=../src/core

OUTPUT_DIR=./build
EXPORT_DIR=./libsynthesizercore

# Compiler stuff
CSYMBOLS=-DBOOST_LOG_DYN_LINK -DAPPLICATION_CONTEXT

#CC=g++
CC=$(XILINX_DIR)/arm-xilinx-linux-gnueabi-g++

#CFLAGS=-Wall -Wno-unused-variable -g3 -fPIC -O2 -isystem"../../Libraries/x86_64/include"
CFLAGS=-Wall -Wno-unused-variable -g3 -fPIC -O2 -isystem"../../Libraries/arm/include" 

#LDFLAGS=-L"../../Libraries/boost_1_54/x86_64/lib"
LDFLAGS=-L"../../Libraries/boost_1_54/arm/lib"

ifneq ($(MAKECMDGOALS),clean)
ifneq ($(strip $(C++_DEPS)),)
-include $(C++_DEPS)
endif
ifneq ($(strip $(C_DEPS)),)
-include $(C_DEPS)
endif
ifneq ($(strip $(CC_DEPS)),)
-include $(CC_DEPS)
endif
ifneq ($(strip $(CPP_DEPS)),)
-include $(CPP_DEPS)
endif
ifneq ($(strip $(CXX_DEPS)),)
-include $(CXX_DEPS)
endif
ifneq ($(strip $(C_UPPER_DEPS)),)
-include $(C_UPPER_DEPS)
endif
endif


# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
$(CORE_SRC_DIR)/BufferedLink.cpp \
$(CORE_SRC_DIR)/ControlLink.cpp \
$(CORE_SRC_DIR)/ControlPort.cpp \
$(CORE_SRC_DIR)/SoundgatesConfig.cpp \
$(CORE_SRC_DIR)/SoundComponent.cpp \
$(CORE_SRC_DIR)/SoundComponentImpl.cpp \
$(CORE_SRC_DIR)/ICallbackFunctor.cpp \
$(CORE_SRC_DIR)/SoundPort.cpp \
$(CORE_SRC_DIR)/Port.cpp \
$(CORE_SRC_DIR)/Link.cpp \
$(CORE_SRC_DIR)/HWSlot.cpp \
$(CORE_SRC_DIR)/HWThreadManager.cpp \
$(CORE_SRC_DIR)/Node.cpp 

OBJS += \
$(OUTPUT_DIR)/BufferedLink.o \
$(OUTPUT_DIR)/ControlLink.o \
$(OUTPUT_DIR)/ControlPort.o \
$(OUTPUT_DIR)/SoundgatesConfig.o \
$(OUTPUT_DIR)/SoundComponent.o \
$(OUTPUT_DIR)/ICallbackFunctor.o \
$(OUTPUT_DIR)/SoundComponentImpl.o \
$(OUTPUT_DIR)/SoundPort.o \
$(OUTPUT_DIR)/Port.o \
$(OUTPUT_DIR)/Link.o \
$(OUTPUT_DIR)/HWSlot.o \
$(OUTPUT_DIR)/HWThreadManager.o \
$(OUTPUT_DIR)/Node.o 

CPP_DEPS += \
$(OUTPUT_DIR)/BufferedLink.d \
$(OUTPUT_DIR)/ControlLink.d \
$(OUTPUT_DIR)/ControlPort.d \
$(OUTPUT_DIR)/SoundgatesConfig.d \
$(OUTPUT_DIR)/ICallbackFunctor.d \
$(OUTPUT_DIR)/SoundComponentImpl.d \
$(OUTPUT_DIR)/SoundComponent.d \
$(OUTPUT_DIR)/SoundPort.d \
$(OUTPUT_DIR)/Port.d \
$(OUTPUT_DIR)/Link.d \
$(OUTPUT_DIR)/HWSlot.d \
$(OUTPUT_DIR)/HWThreadManager.d \
$(OUTPUT_DIR)/Node.d 

all: folderstruct libsynthcore.so copyheaders
	
# Build objects
$(OUTPUT_DIR)/%.o: $(CORE_SRC_DIR)/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	$(CC)  $(CFLAGS) -c $(CSYMBOLS) -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

folderstruct: 
	@mkdir -p $(OUTPUT_DIR)	 
	@mkdir -p $(EXPORT_DIR)/lib 
	@mkdir -p $(EXPORT_DIR)/include 
	

# Link the library together
libsynthcore.so: $(OBJS) 
	@echo 'Building shared core library'
	$(CC) $(LDFLAGS) -g3 -shared -o $(OUTPUT_DIR)/$@  $(OBJS)
	@cp $(OUTPUT_DIR)/$@ $(EXPORT_DIR)/lib/$@

clean:
	rm -rf $(OUTPUT_DIR)/*.o $(OUTPUT_DIR)/*.d $(OUTPUT_DIR)/*.so

.PHONY: all clean

# Export header files
copyheaders: 
	 cp $(CORE_SRC_DIR)/*.h $(EXPORT_DIR)/include
