-include ../../makefiles/common.mk
 
OBJECTS=Mixer.o MixerSW.o

all: libmixer.so
	
libmixer.so: $(OBJECTS)
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@

	