CC=g++
CC_ARM=/opt/Xilinx/14.6/ISE_DS/EDK/gnu/arm/lin/bin/arm-xilinx-linux-gnueabi-g++
CFLAGS=-Wall -Wno-unused-variable -g3 -fPIC -O0 -c 
CFLAGS_ARM=-Wall -Wno-unused-variable -g3 -fPIC -O0 -I"../../libraries/arm/include" 

SOURCES=Soundbuffer.cpp OutputSoundComponent.cpp 
OBJECTS=Soundbuffer.o OutputSoundComponent.o

SOURCES+=../SoundgatesConfig.cpp ../Link.cpp ../SoundComponentImpl.cpp ../Port.cpp ../BufferedLink.cpp ../ControlLink.cpp ../SoundPort.cpp ../ControlPort.cpp
OBJECTS+=SoundgatesConfig.o Link.o SoundComponentImpl.o ControlLink.o ControlPort.o Port.o SoundPort.o BufferedLink.o

all: compile liboutput.so

compile:
	$(CC) $(CFLAGS) $(SOURCES)
	
liboutput.so:
	$(CC) -g3 -shared -o $@  $(OBJECTS) -lasound -lpthread -L/usr/local/lib
	
all_arm: compile_arm liboutput.so_arm

compile_arm:
	$(CC_ARM) $(CFLAGS_ARM) -c $(SOURCES)
    
liboutput.so_arm:
	$(CC_ARM) -g3 -shared -o liboutput.so $(OBJECTS) -lasound -lpthread -L"../../libraries/arm/lib"
	
clean:
	rm -f *.so *.o
