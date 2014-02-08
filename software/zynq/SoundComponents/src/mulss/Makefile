-include ../../makefiles/common.mk

OBJECTS += MulSSComponent.o

all: libmulss.so

libmulss.so: $(OBJECTS)
	@echo $(target)
	$(CC) $(CLINKER_FLAGS) -o $@  $(OBJECTS)

