CC=g++
CFLAGS=-Wall -Wno-unused-variable -g3 -fPIC -O2

SOURCES=SineSoundComponent.cpp impl/SineImpl_SW.cpp impl/SineImpl_HW.cpp  
OBJECTS=SineImpl_SW.o SineImpl_HW.o SineSoundComponent.o

SOURCES+=../SoundComponentImpl.cpp ../Link.cpp ../Port.cpp ../BufferedLink.cpp ../ControlLink.cpp ../SoundPort.cpp ../ControlPort.cpp
OBJECTS+=SoundComponentImpl.o Link.o ControlLink.o ControlPort.o Port.o SoundPort.o BufferedLink.o

all: compile libsine.so

compile:
	$(CC) $(CFLAGS) -c $(SOURCES)
	
libsine.so:
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@
	
clean:
	rm -f *.so *.o
   