CC=g++
CFLAGS=-fPIC -shared

SOURCES=impl/SineImpl_SW.cpp impl/SineImpl_HW.cpp SineSoundComponent.cpp ../SoundComponentImpl.cpp ../Port.cpp
OBJECTS=SoundComponentImpl.o SineImpl_SW.o SineImpl_HW.o SineSoundComponent.o Port.o

all: compile libsine.so

compile:
	$(CC) -Wall -fPIC -O2 -c $(SOURCES)
	
libsine.so:
	$(CC) -shared $(OBJECTS) -o $@
	
clean:
	rm -f *.so *.o
   