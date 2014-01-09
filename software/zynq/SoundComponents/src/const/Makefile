-include ../../makefiles/common.mk


OBJECTS=ConstSoundComponent.o

all: libconst.so

libconst.so: $(OBJECTS)
	$(CC) -g3 -shared -o $@  $(OBJECTS)
	