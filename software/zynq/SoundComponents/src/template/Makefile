# include the common makefile
-include ../../makefiles/common.mk
   
# list of objects that. This will alwyas be <ComponentName>.o and depeneding on implementation
# <ComponentName>_SW.o and/or <ComponentName>_HW.o
OBJECTS=TemplateSoundComponent_SW.o TemplateSoundComponent.o

# all: <nameofyoursharedlibrary>.so
all: libtemplate.so

# <nameofyoursharelibrary>.so: $(OBJECTS)
libtemplate.so: $(OBJECTS) 
	# just leave this like this
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@



# You also need to add a line to ../makefiles/Makefile to include this folder!
	