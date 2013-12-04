CC=g++
CFLAGS=-Wall -Wno-unused-variable -g3 -fPIC -O0 -c 

SOURCES=Soundbuffer.cpp OutputSoundComponent.cpp ../SoundComponentImpl.cpp ../Port.cpp ../BufferedLink.cpp
OBJECTS=SoundComponentImpl.o Soundbuffer.o OutputSoundComponent.o Port.o BufferedLink.o

all: compile liboutput.so

compile:
	$(CC) $(CFLAGS) $(SOURCES)

Samplebuffer:
	$(CC) $(CFLAGS) $(LEGACY_SOURCES)
	
liboutput.so:
	$(CC) -g3 -shared -o $@  $(OBJECTS) -lasound -lpthread -L/usr/local/lib
	
clean:
	rm -f *.so *.o
