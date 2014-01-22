-include ../../makefiles/common.mk

OBJECTS += SoundAddComponent.o

all: libadd.so

libadd.so: $(OBJECTS)
	@echo $(target)
	$(CC) -g3 -shared -o $@  $(OBJECTS)

