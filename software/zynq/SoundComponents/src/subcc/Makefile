-include ../../makefiles/common.mk

OBJECTS += SubCCComponent.o

all: libsubcc.so

libsubcc.so: $(OBJECTS)
	@echo $(target)
	$(CC) $(CLINKER_FLAGS) -o $@  $(OBJECTS)

