-include ../../makefiles/common.mk

OBJECTS += ControlAddControlComponent.o

all: libaddcc.so

libaddcc.so: $(OBJECTS)
	@echo $(target)
	$(CC) $(CLINKER_FLAGS) -o $@  $(OBJECTS)

