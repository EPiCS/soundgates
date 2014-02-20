-include ../../makefiles/common.mk
 
OBJECTS=SoundDelay.o

all:  libdelay.so

libdelay.so: $(OBJECTS)
	$(CC) -g3 -shared -o $@  $(OBJECTS)
	