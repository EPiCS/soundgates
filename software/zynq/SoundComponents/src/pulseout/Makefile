-include ../../makefiles/common.mk
 
OBJECTS=PulseOutputComponent.o

all: libpulseout.so libpulseout.so

libpulseout.so: $(OBJECTS)
	$(CC) $(CLINKER_FLAGS) -o $@  PulseOutputComponent.o -D_REENTRANT  -lpulse-simple -lpulse
	