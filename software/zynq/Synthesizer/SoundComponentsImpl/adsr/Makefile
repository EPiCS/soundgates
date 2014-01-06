CC=g++
CFLAGS=-Wall -Wno-unused-variable -g3 -fPIC -O2

SOURCES=ADSRSoundComponent.cpp impl/ADSRSoundComponent_SW.cpp 
OBJECTS=ADSRSoundComponent_SW.o ADSRSoundComponent.o

SOURCES+=../SoundComponentImpl.cpp ../Link.cpp ../Port.cpp ../BufferedLink.cpp ../ControlLink.cpp ../SoundPort.cpp ../ControlPort.cpp
OBJECTS+=SoundComponentImpl.o Link.o ControlLink.o ControlPort.o Port.o SoundPort.o BufferedLink.o

all: compile libadsr.so

compile:
	$(CC) $(CFLAGS) -c $(SOURCES)
	
libadsr.so:
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@
	
clean:
	rm -f *.so *.o
   