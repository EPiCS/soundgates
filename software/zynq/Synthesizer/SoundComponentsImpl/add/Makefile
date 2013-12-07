CC=g++
CFLAGS=-Wall -Wno-unused-variable -g3 -fPIC -O0 -c 

SOURCES=SoundAddComponent.cpp 
OBJECTS=SoundAddComponent.o

SOURCES+=../SoundComponentImpl.cpp ../Port.cpp ../BufferedLink.cpp ../ControlLink.cpp ../SoundPort.cpp ../ControlPort.cpp
OBJECTS+=SoundComponentImpl.o ControlLink.o ControlPort.o Port.o SoundPort.o BufferedLink.o

all: compile libadd.so

compile:
	$(CC) $(CFLAGS) $(SOURCES)
	
libadd.so:
	$(CC) -g3 -shared -o $@  $(OBJECTS)
	
clean:
	rm -f *.so *.o
