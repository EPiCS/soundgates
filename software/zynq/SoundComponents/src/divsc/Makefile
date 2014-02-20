-include ../../makefiles/common.mk
   
OBJECTS=DivSCComponent.o DivSCComponent_SW.o

all: libdivsc.so

libdivsc.so: $(OBJECTS) 
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@

	