CC=/usr/bin/g++
CC_ARM=/opt/Xilinx/14.6/ISE_DS/EDK/gnu/arm/lin/bin/arm-xilinx-linux-gnueabi-g++
CFLAGS_ARM=-Wall -Wno-unused-variable -g3 -fPIC -O0 -I"../../libraries/arm/include" -I"/opt/Xilinx/14.6/ISE_DS/EDK/gnu/arm/lin/arm-xilinx-linux-gnueabi/include/c++/4.7.2" -DZYNQ
CFLAGS=-Wall -Wno-unused-variable -g3 -fPIC -O2 -I"../../libraries/x86_64/include"

SOURCES=SineSoundComponent.cpp impl/SineImpl_SW.cpp impl/SineImpl_HW.cpp  
OBJECTS=SineImpl_SW.o SineImpl_HW.o SineSoundComponent.o

SOURCES+=../SoundComponentImpl.cpp ../Link.cpp ../Port.cpp ../BufferedLink.cpp ../ControlLink.cpp ../SoundPort.cpp ../ControlPort.cpp
OBJECTS+=SoundComponentImpl.o Link.o ControlLink.o ControlPort.o Port.o SoundPort.o BufferedLink.o

all: compile libsine.so

compile:
	$(CC) $(CFLAGS) -c $(SOURCES)
	
libsine.so:
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@
	
all_arm: compile_arm libarm
	
compile_arm:
	$(CC_ARM) $(CFLAGS_ARM) -c $(SOURCES)
	
libarm:
	$(CC_ARM) -g3 -shared -o libsine.so  $(OBJECTS) -lpthread	
	
clean:
	rm -f *.so *.o
   
