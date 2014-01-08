CC=g++
CFLAGS=-Wall -Wno-unused-variable -g3 -fPIC -O2 -I"../../libraries/x86_64/include"

SOURCES=NoiseSoundComponent.cpp impl/NoiseImplSW.cpp 
OBJECTS=NoiseSoundComponent.o NoiseImplSW.o

SOURCES+=../SoundComponentImpl.cpp ../Link.cpp ../Port.cpp ../BufferedLink.cpp ../ControlLink.cpp ../SoundPort.cpp ../ControlPort.cpp
OBJECTS+=SoundComponentImpl.o Link.o ControlLink.o ControlPort.o Port.o SoundPort.o BufferedLink.o

all: compile libnoise.so

compile:
	$(CC) $(CFLAGS) -c $(SOURCES)
	
libnoise.so:
	$(CC) -g3 -shared -o $@  $(OBJECTS)
	
clean:
	rm -f *.so *.o
