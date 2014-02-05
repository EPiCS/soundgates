# include the common makefile
-include ../../makefiles/common.mk
   
# list of objects that. This will alwyas be <ComponentName>.o and depeneding on implementation
# <ComponentName>_SW.o and/or <ComponentName>_HW.o
OBJECTS=FIR_HW.o FIR_SW.o FIR.o fir_coeff_hp_.o fir_coeff_lp_.o

# all: <nameofyoursharedlibrary>.so
all: libfir.so

# <nameofyoursharelibrary>.so: $(OBJECTS)
libfir.so: $(OBJECTS) 
	# just leave this like this
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@



# You also need to add a line to ../makefiles/Makefile to include this folder!
	