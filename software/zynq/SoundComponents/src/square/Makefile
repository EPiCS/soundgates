-include ../../makefiles/common.mk
   
OBJECTS=SquareSoundComponent_SW.o SquareSoundComponent_HW.o SquareSoundComponent.o
#OBJECTS=SquareSoundComponent_SW.o SquareSoundComponent.o

all: libnco_sq.so

libnco_sq.so: $(OBJECTS) 
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@

	
