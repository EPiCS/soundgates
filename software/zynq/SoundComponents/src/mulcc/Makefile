-include ../../makefiles/common.mk

OBJECTS += MulCCComponent.o

all: libmulcc.so

libmulcc.so: $(OBJECTS)
	@echo $(target)
	$(CC) $(CLINKER_FLAGS) -o $@  $(OBJECTS)

