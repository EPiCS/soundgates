-include ../../makefiles/common.mk
   
OBJECTS=TriangleSoundComponent_HW.o TriangleSoundComponent_SW.o TriangleSoundComponent.o

all: libTriangle.so

libTriangle.so: $(OBJECTS) 
	$(CC) $(CLINKER_FLAGS) -o $@  $(OBJECTS)

	
