---
title: C++ project settings
layout: page
---

# C++ project settings

This page describes the necessary steps to properly compile the C++ part of the Soundgates project.

The project uses the following libraries, which are all included in the [Libraries](https://github.com/pc2/pg-soundgates/tree/master/software/zynq/Libraries) subproject, with both arm and x86_64 versions:
 
 * **alsa** - Interface to play sound _(only arm version included)_
 * **boost 1.54** - Different convenience functions
 * **libav** - Playing sound files
 * **lo** - OSC library
 * **reconos** - Interface to include hardware threads _(only arm version included)_
 * **xmlrpc-c** - XML RPC library. Is not yet used productively
 * **mongo** - A C driver for the mongodb, which is used when sound component logging is enabled

If you use [Eclipse CDT](https://www.eclipse.org/cdt/) as a development environment, the following project settings are already included in the repository and can be imported.      

# Synthesizer  
These settings apply for the Synthesizer project, which will produce the executable that will finally run patches.  
## C++ compiler settings  
### Symbol definitions  
 * `-DBOOST_LOG_DYN_LINK`
 * `-DAPPLICATION_CONTEXT`
Required for the Boost logging.
 * `-DZYNQ`
Only when compiling for the zynq platform  

### Include paths  
 * `-isystem"<path_to>/pg-soundgates/software/zynq/Libraries/x86_64/include"`

Use -isystem instead of -I, otherwise Boost will produce a lot of notification output (or just use both -I and -isystem to be on the safe side)  

### Miscellaneous  
 * `-c`

Link later
  
## C++ Linker settings  

### Dynamically linked libraries  
 * `-L"<path_to>/pg_soundgates/software/zynq/Libraries/x86_64/lib" `
 * `-L"<path_to_reconos>"`
 
If compiling for the Zynq the (TODO Lukas fragen: Sind die Libs nicht im Repo? Da ist noch ein Pfad in den Projekt settings eingetragen. Ließ sich letztens aber auch so auf dem PG Rechner kompilieren)
 
 * `-ldl -lmongoc -llo -lpthread -lboost_filesystem -lboost_thread -lboost_program_options -lboost_regex -lboost_log -lboost_system -lxmlrpc_server_abyss++ -lxmlrpc_server++ -lxmlrpc_server_abyss -lxmlrpc_server -lxmlrpc_abyss -lxmlrpc++ -lxmlrpc -lxmlrpc_util -lxmlrpc_xmlparse -lxmlrpc_xmltok`  

### Miscellaneous  
 * `-rdynamic`
 
The rdynamic flag is required since singletons are used that need to refer to the same object in both the main Synthesizer program as well as in the shared Sound Component libraries.

  
# libsynthesizercore  
A part of synthesizer framework needs to be exposed to sound components. The makefile located at [Synthesizer/makefiles/core.mk](https://github.com/pc2/pg-soundgates/blob/master/software/zynq/Synthesizer/makefiles/core.mk) will build a folder libsynthesizercore, which needs to be copied to the SoundComponents project to compile components there

The core.mk containts definitions for CC, CFLAGS and LDFLAGS, which need to be exchanged depending on whether the lib should be compiled for arm or x86_64.

Building the core happens from the command line via `make -f core.mk`

# Sound Components
Each sound component is compiled as a shared library. Sound components include the common makefile [SoundComponents/makefiles/common.mk](https://github.com/pc2/pg-soundgates/blob/master/software/zynq/SoundComponents/makefiles/common.mk) which sets the necessary flags. 
The sound components can then either be build one by one, or using the top level [Makefile](https://github.com/pc2/pg-soundgates/blob/master/software/zynq/SoundComponents/makefiles/Makefile) which descends into each specified subfolder.

Building happens on the command line with `make target=x86_64|arm debugging=on|off all`.
Debugging will turn optimization of the components off and debugging symbols on and target specifies the target platform.
