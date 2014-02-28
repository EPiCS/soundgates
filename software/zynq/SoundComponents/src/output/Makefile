-include ../../makefiles/common.mk
 
OBJECTS=Soundbuffer.o OutputSoundComponent.o RecordingSoundComponent.o

all: liboutput.so librecord.so

liboutput.so: $(OBJECTS)
	$(CC) $(CLINKER_FLAGS) -o $@  Soundbuffer.o OutputSoundComponent.o -lasound -lpthread
	
librecord.so: $(OBJECTS)
	$(CC) $(CLINKER_FLAGS) -o $@  Soundbuffer.o RecordingSoundComponent.o -lasound -lpthread