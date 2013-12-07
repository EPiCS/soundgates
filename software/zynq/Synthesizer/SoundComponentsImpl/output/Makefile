CC=g++
CFLAGS=-Wall -Wno-unused-variable -g3 -fPIC -O2

SOURCES=Soundbuffer.cpp OutputSoundComponent.cpp 
OBJECTS=Soundbuffer.o OutputSoundComponent.o

SOURCES+=../SoundComponentImpl.cpp ../Link.cpp ../Port.cpp ../BufferedLink.cpp ../ControlLink.cpp ../SoundPort.cpp ../ControlPort.cpp
OBJECTS+=SoundComponentImpl.o Link.o ControlLink.o ControlPort.o Port.o SoundPort.o BufferedLink.o

all: compile liboutput.so

compile:
	$(CC) $(CFLAGS) -c $(SOURCES)
	
liboutput.so:
	$(CC) -g3 -shared -o $@  $(OBJECTS) -lasound -lpthread -L/usr/local/lib
	
clean:
	rm -f *.so *.o
