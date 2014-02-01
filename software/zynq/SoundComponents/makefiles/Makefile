SoundComponents += add \
				  adsr \
				  const	\
				  delay \
				  fir \
				  mixer\
				  noise \
				  output \
				  sine \
				  mulsc \
				  output \
				  square \
				  dummysink \
				sampleplayer \
                soundToControl \
				template \
				timer \
				saw \
				pwm


SRCDIR = ../src
DESTDIR= ../result

SUBDIRS = $(addprefix $(SRCDIR)/, $(SoundComponents) )
# make all target=x86 


all:
	@mkdir -p $(DESTDIR)
	@echo 'Building sound components' 
	for dir in $(SUBDIRS); do \
       $(MAKE) all -C $$dir;      \
	done 
	@echo 'Copy sound components to repository'
	@find $(SRCDIR) -name "*.so" -exec cp {} $(DESTDIR) \;
   
clean:
	for dir in $(SUBDIRS); do \
       $(MAKE) clean -C $$dir;      \
    done
	@rm -f $(DESTDIR)/*.so
   
.PHONY: clean   
#$(addprefix lib, $(SoundComponents) )
#$(addsuffix .so, $(SoundComponents) )
