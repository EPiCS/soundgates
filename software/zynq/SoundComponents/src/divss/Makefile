-include ../../makefiles/common.mk

OBJECTS += DivSSComponent.o

all: libdivss.so

libdivss.so: $(OBJECTS)
	@echo $(target)
	$(CC) $(CLINKER_FLAGS) -o $@  $(OBJECTS)

