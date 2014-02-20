-include ../../makefiles/common.mk
 
OBJECTS=Soundbuffer.o OutputSoundComponent.o

all: liboutput.so

liboutput.so: $(OBJECTS)
	$(CC) $(CLINKER_FLAGS) -o $@  $(OBJECTS) -lasound -lpthread
