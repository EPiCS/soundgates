-include ../../makefiles/common.mk
 
OBJECTS +=ADSRSoundComponent_SW.o \
		  ADSR_HW.o \
		  ADSRSoundComponent.o 

all: libadsr.so
	
libadsr.so: $(OBJECTS)
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@
	