Build
-----------------------

1. Change directory into 'makefiles'
2. Call 'make all target=XXX' where XXX is one 'arm' or 'x86'
3. Result will be copied into 'result'

For cleaning up call 'make clean'

Add new components
-----------------------

1. Create a new folder in the 'src' directory and name it with your component name
2. Create an 'impl' subfolder that contains your implementations
3. Add a class which implements the 'SoundComponentImpl' interface
4. Add component shortname, ports, etc. 
5. If you have no specific HW/SW implementation you can stop here. 
   Add the 'EXPORT_SOUNDCOMPONENT_NO_IMPL' to your implementation and goto step 7.  
   Otherwise goto 6.
6. If you have a specific SW or HW implementation you should further subclass your component.
   Add these subclasses to your 'impl' folder and name them 'YourComponentClass_SUFFIX'. SUFFIX
   can be 'HW' or 'SW'. 
   Use 'EXPORT_SOUNDCOMPONENT_MIXED_IMPL' if you want to export both implementations. 
   
7. Add the component to the list in 'makefiles/Makefile'
