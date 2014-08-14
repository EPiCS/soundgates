-include ../../makefiles/common.mk
 
OBJECTS=NoiseSoundComponent.o NoiseImplSW.o Noise_HW.o

all: libnoise.so

libnoise.so: $(OBJECTS)
	$(CC) $(CLINKER_FLAGS) $(OBJECTS) -o $@ 
	