-include ../../makefiles/common.mk
 
OBJECTS=NoiseSoundComponent.o NoiseImplSW.o

all: libnoise.so

libnoise.so: $(OBJECTS)
	$(CC) $(CLINKER_FLAGS) -o $@ $(OBJECTS)
	