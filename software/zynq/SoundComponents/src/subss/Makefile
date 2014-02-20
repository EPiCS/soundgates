-include ../../makefiles/common.mk

OBJECTS += SubSSComponent.o

all: libsubss.so

libsubss.so: $(OBJECTS)
	@echo $(target)
	$(CC) $(CLINKER_FLAGS) -o $@  $(OBJECTS)

