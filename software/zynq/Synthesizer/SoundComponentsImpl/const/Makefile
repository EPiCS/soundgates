CC=g++
CFLAGS=-Wall -Wno-unused-variable -g3 -fPIC -O2

SOURCES=ConstSoundComponent.cpp
OBJECTS=ConstSoundComponent.o

SOURCES+=../SoundComponentImpl.cpp ../Link.cpp ../Port.cpp ../BufferedLink.cpp ../ControlLink.cpp ../SoundPort.cpp ../ControlPort.cpp
OBJECTS+=SoundComponentImpl.o Link.o ControlLink.o ControlPort.o Port.o SoundPort.o BufferedLink.o

all: compile libconst.so

compile:
	$(CC) $(CFLAGS) -c $(SOURCES)
	
libconst.so:
	$(CC) -g3 -shared -o $@  $(OBJECTS)
	
clean:
	rm -f *.so *.o
