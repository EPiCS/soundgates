-include ../../makefiles/common.mk
   
OBJECTS=SawtoothSoundComponent_SW.o SawtoothSoundComponent.o

all: libnco_saw.so

libnco_saw.so: $(OBJECTS) 
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@

	
