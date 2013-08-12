CFLAGS=-O3 -g -Wall
CC=microblaze-unknown-linux-gnu-gcc
TARGET=soundgates
APPLICATION_SRC=notes.h soundgates.c
RECONOS := $(shell echo $(RECONOS))

all: $(TARGET)

$(TARGET): $(TARGET).c
	$(CC) $(CFLAGS) -L $(RECONOS)/linux/reconos/libreconos-us -I $(RECONOS)/linux/reconos/libreconos-us $(APPLICATION_SRC) -o $(TARGET) -static -lreconos -lpthread -lrt -lm

clean:
	rm -f *.o $(TARGET)
