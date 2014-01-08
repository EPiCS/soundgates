CC=/usr/bin/g++
CC_ARM=/opt/Xilinx/14.6/ISE_DS/EDK/gnu/arm/lin/bin/arm-xilinx-linux-gnueabi-g++
CFLAGS_ARM=-Wall -Wno-unused-variable -g3 -fPIC -O0 -I"../../libraries/arm/include" 
CFLAGS=-Wall -Wno-unused-variable -g3 -fPIC -O2 -I"../../libraries/x86_64/include"

SOURCES=DummySink.cpp
OBJECTS=DummySink.o

SOURCES+=../SoundComponentImpl.cpp ../Link.cpp ../Port.cpp ../BufferedLink.cpp ../ControlLink.cpp ../SoundPort.cpp ../ControlPort.cpp
OBJECTS+=SoundComponentImpl.o Link.o ControlLink.o ControlPort.o Port.o SoundPort.o BufferedLink.o

all: compile libdummysink.so

compile:
	$(CC) $(CFLAGS) -c $(SOURCES)
	
libdummysink.so:
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@
	
all_arm: compile_arm libarm
	
compile_arm:
	$(CC_ARM) $(CFLAGS_ARM) -c $(SOURCES)
	
libarm:
	$(CC_ARM) -g3 -shared -o libdummysink.so  $(OBJECTS) -lpthread	
	
clean:
	rm -f *.so *.o
   