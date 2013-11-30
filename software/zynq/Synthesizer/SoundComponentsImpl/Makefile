CC=g++

all: libsine.so

libsine.so:
	$(CC) -fPIC -shared sine/impl/SineImpl_SW.cpp sine/impl/SineImpl_HW.cpp sine/SineSoundComponent.cpp SoundComponentImpl.cpp -o libsine.so	
	
clean:
	rm -f *.so *.o
   
 