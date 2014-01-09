-include ../../makefiles/common.mk

OBJECTS=DummySink.o

all: libdummysink.so

libdummysink.so: $(OBJECTS)
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@
	