-include ../../makefiles/common.mk

OBJECTS += SoundAddComponent.o

all: libaddss.so

libaddss.so: $(OBJECTS)
	@echo $(target)
	$(CC) $(CLINKER_FLAGS) -o $@  $(OBJECTS)

