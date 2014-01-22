-include ../../makefiles/common.mk
 
OBJECTS=Soundbuffer.o OutputSoundComponent.o

all: liboutput.so

liboutput.so: $(OBJECTS)
	$(CC) -g3 -shared -o $@  $(OBJECTS) -lasound -lpthread -L/usr/local/lib
