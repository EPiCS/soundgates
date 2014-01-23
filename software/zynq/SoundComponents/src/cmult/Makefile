-include ../../makefiles/common.mk
   
OBJECTS=MultiplyControl.o MultiplyControlSW.o

all: libcmult.so

libcmult.so: $(OBJECTS) 
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@

	