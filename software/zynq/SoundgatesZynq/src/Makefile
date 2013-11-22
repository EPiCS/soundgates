# needed environment variables
# (shold be set by the reconos toolchain)
# CROSS_COMPILE
CC = $(CROSS_COMPILE)gcc

CFLAGS += -O0 -g -Wall -static -L $(RECONOS)/linux/lib -I $(RECONOS)/linux/lib/include

TARGET=SynthesizerTest
APPLICATION_SRC=SynthesizerTest.c Samplebuffer.c ComponentStructs.h Samplebuffer_getset.c

all: $(TARGET)

$(TARGET): $(TARGET)
	$(CC) $(CFLAGS) $(APPLICATION_SRC) -o $(TARGET) -lreconos -lpthread -lrt -lm

clean:
	rm -f *.o SynthesizerTest

%.o: %.c
	$(CC) -c $(CFLAGS) -o $@ $<



	
