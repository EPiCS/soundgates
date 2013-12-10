CC=g++
CFLAGS=-Wall -Wno-unused-variable -g3 -fPIC -O0 -c 

SOURCES=Soundbuffer.cpp OutputSoundComponent.cpp 
OBJECTS=Soundbuffer.o OutputSoundComponent.o

SOURCES+=../SoundgatesConfig.cpp ../Link.cpp ../SoundComponentImpl.cpp ../Port.cpp ../BufferedLink.cpp ../ControlLink.cpp ../SoundPort.cpp ../ControlPort.cpp
OBJECTS+=SoundgatesConfig.o Link.o SoundComponentImpl.o ControlLink.o ControlPort.o Port.o SoundPort.o BufferedLink.o

all: compile liboutput.so

compile:
	$(CC) $(CFLAGS) $(SOURCES)
	
liboutput.so:
	$(CC) -g3 -shared -o $@  $(OBJECTS) -lasound -lpthread -L/usr/local/lib
	
clean:
	rm -f *.so *.o
