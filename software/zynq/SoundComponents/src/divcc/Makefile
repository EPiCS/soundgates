-include ../../makefiles/common.mk

OBJECTS += DivCCComponent.o

all: libdivcc.so

libdivcc.so: $(OBJECTS)
	@echo $(target)
	$(CC) $(CLINKER_FLAGS) -o $@  $(OBJECTS)

