---
title: Creation of sound components
layout: page
---

# General
Sound components are compiled as shared libraries and can be dynamically loaded at runtime. Using such an approach, the sound components exist separately from the implementation of the Synthesizer framework with only the necessary core headers included. Developers can therefore easily create new sound components and distribute them to users of the Soundgates system.

The following sections describe implementation details of a sound component. 

# Software implementation
## General structure
Each sound component needs to be a subclass of [SoundComponentImpl](https://github.com/pc2/pg-soundgates/blob/master/software/zynq/Synthesizer/src/core/SoundComponentImpl.h). Some constraints have to be met to create a fully functional component.
A [template component](https://github.com/pc2/pg-soundgates/tree/master/software/zynq/SoundComponents/src/template) is available, that includes most of the following things:

* There needs to be a `public static const char*` field `name` and a `getName()` method. These will be used determine which component to initialize when a [TGF](TGF-Format) file is parsed and in logging. Macros `DECLARE_COMPONENTNAME` and `DEFINE_COMPONENTNAME(<classname>, <exported name>)`  can be used to facilitate the inclusion of this.
* Ports need to added to their respective list (input or output). The macros `DECLARE_PORT3( SoundPort|ControlPort, <port name>, <port number>)` and `CREATE_AND_REGISTER_PORT3(<classname>, In|Out, SoundPort|ControlPort, <port name>, <port number>)` facilitate this process. These macros declare and initialize ports with names `m_<portname>_<Direction>_Port`.
 * The numbering on the ports should start at one have no gaps. The numbering only contributes to the variable name, but wrong numbering might lead to confusion in other places.
 * Input ports and output ports are added to different lists, while control and sound ports are added to the same.
* Depending on whether or not the component should only be available as software, hardware or as both, the remaining procedure differs a bit, to offer corresponding create methods:
 * If the component will only ever be a software component, the actual processing and initialization can happen within the same class by implementing the `init()` and `process()` functions. 
`EXPORT_SOUNDCOMPONENT_NO_IMPL(<classname>)` needs to be added to the header file in this case.
 * Otherwise, `EXPORT_SOUNDCOMPONENT_SW_ONLY`, `..._HW_ONLY` or `..._MIXED_IMPL` need to be used and a subfolder impl with subclasses called `<classname>_SW` or `<classname>_HW` need to created. 

## Sample manipulation
* Usually a sound component will touch all samples. The number of samples in each processing step can be retrieved from `Synthesizer::config::blocksize`
* Access a the _i-th_ sample: `(*sound_port_variable)[i]`
* Write the _i-th_ sample: `<sound_port_variable>->writeSample(<value>,i)`
* Or operate directly on the underlying arrays with `sound_port_variable->getRead/WriteBuffer()`
 * When operating directly on the buffers, it is important to note that they are of type char. Casting to int might be required.

## Control values
* All control values are of type float.
* Access a control value: `control_port_variable->pop()`
* Write a control value: `control_port_variable->push(<value>)`
* Sound components should register a callback interface to react on changing incoming control values instead of accessing them directly in their process methods. 
 * Changing values are therefore propagated immediately if incoming control values trigger changes in outgoing control values.
 * Calculations on control values only need to be done each time an input changes and not in every process() step.

## Reading static parameters
Static parameters (e.g. the value of a [constant](https://github.com/pc2/pg-soundgates/blob/development/software/zynq/SoundComponents/src/const/ConstSoundComponent.cpp)) are passed in to the sound components constructor as `std::vector<std::string> params`. The float values need to be parsed from this string.

## Building
The makefile for a component only needs to include makefiles/common.mk, specify the OBJECTS to build and a rule for a shared library (cf. [template makefile](https://github.com/pc2/pg-soundgates/blob/development/software/zynq/SoundComponents/src/template/Makefile))
Note that you must have the core library and includes (libsynthesizercore) in your SoundComponents projects to build components, which described in the [project settings](https://github.com/pc2/pg-soundgates/wiki/Cplusplus-project-settings).

# Hardware implementation
What steps are necessary to bring the component to an FPGA?

# Pure Data simulation
What do I have to do to create a pd equivalent to our component?

# Editor integration
To use a sound component in the editor you need a XML file that contains the description of it. The XML file contains the following informations:  
- Name
- Ports with their attributes
- Internal name(s)
- Port mappings for the software and the hardware implementations  

I will explain the concrete syntax of such a XML file on an example. Consider the sound component "Mixer". The first tag is the "Type" tag which contains the type of the component. This component type is used in the editor.
```<Type> FIR </Type>
```