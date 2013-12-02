CC=g++
CFLAGS=-Wall -fPIC -O2 -c 
SOURCES=OutputSoundComponent.cpp ../SoundComponentImpl.cpp ../Port.cpp ../BufferedLink.cpp
LEGACY_SOURCES=Samplebuffer.c Samplebuffer_getset.c 
OBJECTS=SoundComponentImpl.o Samplebuffer_getset.o Samplebuffer.o OutputSoundComponent.o Port.o BufferedLink.o

all: compile liboutput.so

compile:
	$(CC) $(CFLAGS) $(SOURCES)

Samplebuffer:
	gcc $(CFLAGS) $(LEGACY_SOURCES)
	
liboutput.so: Samplebuffer $(OBJECTS)
	$(CC) -shared -o $@  $(OBJECTS) -lasound -lpthread -L/usr/local/lib
	
clean:
	rm -f *.so *.o
