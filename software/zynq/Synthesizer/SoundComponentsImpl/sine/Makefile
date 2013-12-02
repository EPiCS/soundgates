CC=g++
CFLAGS=-fPIC -shared -g3

SOURCES=impl/SineImpl_SW.cpp impl/SineImpl_HW.cpp SineSoundComponent.cpp ../SoundComponentImpl.cpp ../Port.cpp ../BufferedLink.cpp
OBJECTS=SoundComponentImpl.o SineImpl_SW.o SineImpl_HW.o SineSoundComponent.o Port.o BufferedLink.o

all: compile libsine.so

compile:
	$(CC) -Wall -g3 -fPIC -O0 -c $(SOURCES)
	
libsine.so:
	$(CC) -g3 -shared $(OBJECTS) -o $@
	
clean:
	rm -f *.so *.o
   