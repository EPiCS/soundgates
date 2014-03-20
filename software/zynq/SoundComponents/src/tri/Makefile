-include ../../makefiles/common.mk
   
OBJECTS=TriangleSoundComponent_SW.o TriangleSoundComponent_HW.o TriangleSoundComponent.o

all: libTriangle.so

libTriangle.so: $(OBJECTS) 
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@

	
