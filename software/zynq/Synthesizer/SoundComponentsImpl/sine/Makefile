CC=g++
CFLAGS=-fPIC -Wno-unused-variable -shared -g3

SOURCES=SineSoundComponent.cpp impl/SineImpl_SW.cpp impl/SineImpl_HW.cpp  
OBJECTS=SineImpl_SW.o SineImpl_HW.o SineSoundComponent.o

SOURCES+=../SoundComponentImpl.cpp ../Port.cpp ../BufferedLink.cpp ../ControlLink.cpp ../SoundPort.cpp ../ControlPort.cpp
OBJECTS+=SoundComponentImpl.o ControlLink.o ControlPort.o Port.o SoundPort.o BufferedLink.o

all: compile libsine.so

compile:
	$(CC) -Wall -Wno-unused-variable -g3 -fPIC -O0 -c $(SOURCES)
	
libsine.so:
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@
	
clean:
	rm -f *.so *.o
   