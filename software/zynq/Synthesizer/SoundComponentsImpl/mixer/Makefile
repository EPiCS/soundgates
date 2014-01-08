CC=g++
CFLAGS=-Wall -Wno-unused-variable -g3 -fPIC -O2 -I"../../libraries/x86_64/include"

SOURCES=Mixer.cpp impl/MixerSW.cpp 
OBJECTS=Mixer.o MixerSW.o

SOURCES+=../SoundComponentImpl.cpp ../Link.cpp ../Port.cpp ../BufferedLink.cpp ../ControlLink.cpp ../SoundPort.cpp ../ControlPort.cpp
OBJECTS+=SoundComponentImpl.o Link.o ControlLink.o ControlPort.o Port.o SoundPort.o BufferedLink.o

all: compile libmixer.so

compile:
	$(CC) $(CFLAGS) -c $(SOURCES)
	
libmixer.so:
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@
	
clean:
	rm -f *.so *.o
   