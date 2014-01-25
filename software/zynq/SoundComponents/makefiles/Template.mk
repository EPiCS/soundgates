-include ../../makefiles/common.mk

OBJECTS += MySoundcomponent.o impl/MySoundComponent_SW.o impl/MySoundComponent_HW.o

# Do whatever you want to build your component...

all: lib_MyFancySoundComponent.so


lib_MyFancySoundComponent.so: $(OBJECTS)
	@echo $(target)
	$(CC) -g3 -shared -o $@  $(OBJECTS)

