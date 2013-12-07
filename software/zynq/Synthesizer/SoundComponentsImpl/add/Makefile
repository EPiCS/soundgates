CC=g++
CFLAGS=-Wall -Wno-unused-variable -g3 -fPIC -O2

SOURCES=SoundAddComponent.cpp 
OBJECTS=SoundAddComponent.o

SOURCES+=../SoundComponentImpl.cpp ../Link.cpp ../Port.cpp ../BufferedLink.cpp ../ControlLink.cpp ../SoundPort.cpp ../ControlPort.cpp
OBJECTS+=SoundComponentImpl.o Link.o ControlLink.o ControlPort.o Port.o SoundPort.o BufferedLink.o

all: compile libadd.so

compile:
	$(CC) $(CFLAGS) -c $(SOURCES)
	
libadd.so:
	$(CC) -g3 -shared -o $@  $(OBJECTS)
	
clean:
	rm -f *.so *.o
