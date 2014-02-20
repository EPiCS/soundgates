-include ../../makefiles/common.mk

OBJECTS += SubSCComponent.o

all: libsubsc.so

libsubsc.so: $(OBJECTS)
	@echo $(target)
	$(CC) $(CLINKER_FLAGS) -o $@  $(OBJECTS)

