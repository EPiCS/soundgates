-include ../../makefiles/common.mk
 
OBJECTS=SamplePlayer_SW.o SamplePlayer.o

LIBRARY=-lavcodec -lavformat

CFLAGS +=-D__STDC_CONSTANT_MACROS

all: libsampleplayer.so
	
libsampleplayer.so: $(OBJECTS)
	$(CC) $(CLINKER_FLAGS) $(OBJECTS) -o $@ $(LIBRARY)
   
