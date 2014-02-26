-include ../../makefiles/common.mk

OBJECTS += AccumulatorCC.o

all: libacccc.so

libacccc.so: $(OBJECTS)
	@echo $(target)
	$(CC) $(CLINKER_FLAGS) -o $@  $(OBJECTS)

