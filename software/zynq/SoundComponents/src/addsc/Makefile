-include ../../makefiles/common.mk

OBJECTS += SoundAddControlComponent.o

all: libaddsc.so

libaddsc.so: $(OBJECTS)
	@echo $(target)
	$(CC) $(CLINKER_FLAGS) -o $@  $(OBJECTS)

