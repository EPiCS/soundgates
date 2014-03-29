# include the common makefile
-include ../../makefiles/common.mk
   
OBJECTS=PwmSoundComponent_HW.o PwmSoundComponent_SW.o PwmSoundComponent.o

all: libpwm.so

libpwm.so: $(OBJECTS) 
	# just leave this like this
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@



# You also need to add a line to ../makefiles/Makefile to include this folder!
	