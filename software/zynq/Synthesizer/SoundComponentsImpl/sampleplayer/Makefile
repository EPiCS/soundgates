CC=g++
CFLAGS=-Wall -Wno-unused-variable -g3 -fPIC -O2

SOURCES=SamplePlayer.cpp impl/SamplePlayer_SW.cpp 
OBJECTS=SamplePlayer_SW.o SamplePlayer.o

SOURCES+=../SoundComponentImpl.cpp ../Link.cpp ../Port.cpp ../BufferedLink.cpp ../ControlLink.cpp ../SoundPort.cpp ../ControlPort.cpp
OBJECTS+=SoundComponentImpl.o Link.o ControlLink.o ControlPort.o Port.o SoundPort.o BufferedLink.o

LIBRARY=-lavcodec -lavformat 

all: compile libsmplply.so

compile:
	$(CC) $(CFLAGS) -c $(SOURCES)
	
libsmplply.so:
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@ -L/usr/lib/x86_64-linux-gnu $(LIBRARY)
	
clean:
	rm -f *.so *.o
   