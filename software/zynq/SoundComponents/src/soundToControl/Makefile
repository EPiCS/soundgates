# include the common makefile
-include ../../makefiles/common.mk
   
# list of objects that. This will alwyas be <ComponentName>.o and depeneding on implementation
# <ComponentName>_SW.o and/or <ComponentName>_HW.o
OBJECTS=SoundToControlComponent_SW.o SoundToControlComponent.o

# all: <nameofyoursharedlibrary>.so
all: libsoundtocontrol.so

# <nameofyoursharelibrary>.so: $(OBJECTS)
libsoundtocontrol.so: $(OBJECTS) 
	# just leave this like this
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@



# You also need to add a line to ../makefiles/Makefile to include this folder!
	