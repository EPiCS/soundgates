-include ../../makefiles/common.mk

OBJECTS += SoundAddComponent.o

all: libadd.so

libadd.so: $(OBJECTS)
	@echo $(target)
	$(CC) $(CLINKER_FLAGS) -o $@  $(OBJECTS)

