-include ../../makefiles/common.mk
 
OBJECTS +=Ramp_SW.o \
		  Ramp.o

all: libramp.so
	
libramp.so: $(OBJECTS)
	$(CC) $(CLINKER_FLAGS) -o $@  $(OBJECTS)
	