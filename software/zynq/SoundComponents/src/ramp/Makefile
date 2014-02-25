-include ../../makefiles/common.mk
 
OBJECTS +=Ramp_SW.o \
		  Ramp.o

all: libramp.so
	
libramp.so: $(OBJECTS)
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@
	