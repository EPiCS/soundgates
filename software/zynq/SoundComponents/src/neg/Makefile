-include ../../makefiles/common.mk

OBJECTS += NegationComponent.o

all: libneg.so

libneg.so: $(OBJECTS)
	@echo $(target)
	$(CC) $(CLINKER_FLAGS) -o $@  $(OBJECTS)

