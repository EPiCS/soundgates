CC=/usr/bin/g++
CC_ARM=/opt/Xilinx/14.6/ISE_DS/EDK/gnu/arm/lin/bin/arm-xilinx-linux-gnueabi-g++

CFLAGS=-Wall -Wno-unused-variable -g3 -fPIC -O2
CFLAGS_ARM=-Wall -Wno-unused-variable -g3 -fPIC -O0 -I"../../libraries/arm/include" 

SOURCES=ConstSoundComponent.cpp
OBJECTS=ConstSoundComponent.o

SOURCES+=../SoundComponentImpl.cpp ../Link.cpp ../Port.cpp ../BufferedLink.cpp ../ControlLink.cpp ../SoundPort.cpp ../ControlPort.cpp
OBJECTS+=SoundComponentImpl.o Link.o ControlLink.o ControlPort.o Port.o SoundPort.o BufferedLink.o

all: compile libconst.so

compile:
	$(CC) $(CFLAGS) -c $(SOURCES)
	
libconst.so:
	$(CC) -g3 -shared -o $@  $(OBJECTS)
	
all_arm: compile_arm libconst.so_arm
	
compile_arm:
	$(CC_ARM) $(CFLAGS_ARM) -c $(SOURCES)
	
libconst.so_arm:
	$(CC_ARM) -g3 -shared -o libconst.so  $(OBJECTS) 

clean:
	rm -f *.so *.o
