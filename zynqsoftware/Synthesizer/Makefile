CFLAGS=-O3 -g -Wall
# Anpassungen noch notwendig ;)
CC=microblaze-unknown-linux-gnu-gcc
TARGET=synthesizer
APPLICATION_SRC=synthesizer.c
RECONOS := $(shell echo $(RECONOS))

all: $(TARGET)

$(TARGET): $(TARGET).c
	$(CC) $(CFLAGS) -L $(RECONOS)/linux/reconos/libreconos-us -I $(RECONOS)/linux/reconos/libreconos-us $(APPLICATION_SRC) -o $(TARGET) -static -lreconos -lpthread -lrt -lm

clean:
	rm -f *.o $(TARGET)
