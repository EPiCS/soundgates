# include the common makefile
-include ../../makefiles/common.mk
   
# list of objects that. This will alwyas be <ComponentName>.o and depeneding on implementation
# <ComponentName>_SW.o and/or <ComponentName>_HW.o
OBJECTS=PwmSoundComponent_SW.o PwmSoundComponent.o

# all: <nameofyoursharedlibrary>.so
all: libpwm.so

# <nameofyoursharelibrary>.so: $(OBJECTS)
libpwm.so: $(OBJECTS) 
	# just leave this like this
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@



# You also need to add a line to ../makefiles/Makefile to include this folder!
	