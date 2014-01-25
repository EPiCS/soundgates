-include ../../makefiles/common.mk
   
OBJECTS=SineSoundComponent_SW.o SineSoundComponent_HW.o SineSoundComponent.o

all: libsine.so

libsine.so: $(OBJECTS) 
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@

	