CC=/usr/bin/g++
CC_ARM=/opt/Xilinx/14.6/ISE_DS/EDK/gnu/arm/lin/bin/arm-xilinx-linux-gnueabi-g++
CFLAGS=-Wall -Wno-unused-variable -g3 -fPIC -O2
CFLAGS_ARM=-Wall -Wno-unused-variable -g3 -fPIC -O0 -I"../../libraries/boost_1_54/arm/include" 

LDFLAGS_ARM=-L"../../libraries/boost_1_54/arm/lib"

SOURCES=SoundAddComponent.cpp 
OBJECTS=SoundAddComponent.o

SOURCES+=../SoundComponentImpl.cpp ../Link.cpp ../Port.cpp ../BufferedLink.cpp ../ControlLink.cpp ../SoundPort.cpp ../ControlPort.cpp
OBJECTS+=SoundComponentImpl.o Link.o ControlLink.o ControlPort.o Port.o SoundPort.o BufferedLink.o

all: compile libadd.so

compile:
	$(CC) $(CFLAGS) -c $(SOURCES)
	
libadd.so:
	$(CC) -g3 -shared -o $@  $(OBJECTS)
	
all_arm: compile_arm libadd.so_arm
	
compile_arm:
	$(CC_ARM) $(CFLAGS_ARM) -c $(SOURCES)
	
libadd.so_arm:
	$(CC_ARM) -g3 -shared -o libadd.so  $(OBJECTS) -lpthread
	
clean:
	rm -f *.so *.o
