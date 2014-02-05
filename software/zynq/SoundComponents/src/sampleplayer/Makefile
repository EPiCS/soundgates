-include ../../makefiles/common.mk
 
OBJECTS=SamplePlayer_SW.o SamplePlayer.o

LIBRARY=-lavcodec -lavformat 

CFLAGS +=-D__STDC_CONSTANT_MACROS

all: libsampleplayer.so
	
libsampleplayer.so: $(OBJECTS)
	$(CC) -g3 -Wno-unused-variable -shared $(OBJECTS) -o $@ -L../../../Libraries/x86_64/lib $(LIBRARY)
   
