-include ../../makefiles/common.mk
 
OBJECTS=NoiseSoundComponent.o NoiseImplSW.o

all: libnoise.so

libnoise.so: $(OBJECTS)
	$(CC) -g3 -shared -o $@ $(OBJECTS)
	