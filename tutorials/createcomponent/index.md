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
This tutorial is based on the template for Hardware Thread sound components which you can find [here](https://github.com/pc2/pg-soundgates/tree/development/hardware/hwt/pcores/hwt_template_v1_00_a/hdl/vhdl).

## Create Basic VHDL Component

All your component needs is one (or more) _output_ and one _enable_ port. Additionally one or more input ports are possible. 

For the following we assume, that we already have an inplemented component (`BASIC_COMPONENT`) with the following ports.

* `clk : in std_logic;`
* `rst : in std_logic;`
* `ce : in std_logic;`
* `input : in signed(31 downto 0);` for incoming samples
* `param : in signed(31 downto 0);` as another random parameter
* `output : in signed(31 downto 0);` for modified samples


## Embed Basic Component into ReConOS Hardware Thread
### General Functionality
Each HWT is attached to a FIFO in order to receive data from the Synthesizer. In order to use them for calculations, the HWT copies this data to its local RAM, where the samples, parameters etc are read as required.

### General Structure
The HWT's processing takes place in the state machine in the process `HWT_CTRL_FSM_PROC`. Basically, the FSM consists of the following states:
* `STATE_IDLE`, where the HWT waits for the Synthesizer to start the HWT
* `STATE_REFRESH_HWT_ARGS`, where the HWT loads the Synthesizer's data (parameters etc) to local signals
* `STATE_READ`, where the HWT loads the incoming samples to local RAM
* `STATE_PROCESS`, where the basic component is enabled to create/modify samples
* `STATE_WRITE_MEM`, where the created/modified samples are written back to the FIFO
* `STATE_NOTIFY`, where the Synthesizer is informed, that the HWT is finished
* `STATE_EXIT`, where the HWT is terminated

### Example
You can see the basic component's embedment [here](https://github.com/pc2/pg-soundgates/blob/development/hardware/hwt/pcores/hwt_template_v1_00_a/hdl/vhdl/hwt_template.vhd). 

### How does the FSM work?
* `STATE_IDLE`: Wait for a Messagebox from the Synthesizer.
* `STATE_REFRESH_HWT_ARGS`: The HWT's arguments/parameters are refreshed. In this example, they arguments contain `sourceaddress`, where samples are read, `destaddress`, where samples are written and `param`, which is a random argument. These arguments are stored in the signals `hwtio.argv(0)`, `hwtio.argv(1)` and `hwtio.argv(3)`.
* `STATE_READ`: According to the source address in `hwtio.argv(0)` 64 samples are read into local RAM
* `STATE_PROCESS`: The signal `o_RAMAddr_hwt` is the address to the current sample in the local RAM, which is increased after enabling the basic component. This address is pointing to a sample stored in the signal `i_RAMData_hwt`. In order to write a sample, the signal `o_RAMData_hwt` needs to be assigned.
* `STATE_WRITE_MEM`: Write local RAM back to FIFO
* `STATE_NOTIFY`: Notify Synthesizer

### How to bind signals to the component's ports?
* `clk` and `rst` are wired to the Clock and Reset signal from the HWT
* `ce` is wired to a local signal, also called `ce`
* `input` is wired to `i_RAMData_hwt`, which depicts the current value from the local RAM, on which `o_RAMAddr_hwt` points
* `param` is wired to `hwtio.argv(2)`
* `output` is a driver for the signal `o_RAMData_hwt`. This stores the modified sample to the local RAM.

## Create a PCore
In order to use your HWT, you need to create a directory [here](https://github.com/pc2/pg-soundgates/tree/development/hardware/hwt/pcores), which contains the following to directories:

* `data` - adjust MPD, PAO and TCL files and store them here
* `hdl/vhdl` -  your VHDL files (basic component, HWT) are stored here

## Create Drivers for the Synthesizer

# Pure Data simulation
What do I have to do to create a pd equivalent to our component?

# Editor integration
To use a sound component in the editor you need a XML file that contains the description of it. The XML file contains the following informations:  

* Name
* Ports with their attributes
* Internal name(s)
* Port mappings for the software and the hardware implementations  

I will explain the concrete syntax of such a XML file on an example. Consider the sound component fir filter. The first tag is the "Type" tag which contains the type of the component.

```
	<Type> FIR </Type>
```

The next tag "Category" is used in the editor to put the components in categories. If the XML description does not have this tag, the component is put into the category "General".

```
	<Category> Filter </Category>
```

The tag "Ports" contains the list of the component's ports. Each port has its own tag "Port". It has attributes "Name", "DataType" and "Direction".

``` 
	<Ports>
		<Port DataType="SOUND" Direction="IN" Name="SoundIn"/>
		<Port DataType="CONTROL" Direction="IN" Name="CutoffFrequency"/>
		<Port DataType="SOUND" Direction="OUT" Name="SoundOut"/>
	</Ports>
```

The tag "Properties" is optional since only some components have static properties. Each property has a name (tag "Name"). A property can have an initial value (tag "InitialValue") which is optional. Their are two kinds of static properties: 

1. Float properties are described with the tag "FloatProperty". The value of such a property is a float number.
2. String properties are described with the tag "UserStringProperty". The value of such a property can be an arbitrary text. A string property can have predefined options, as you see in the sound component "FIR":

```	
	<Properties>
		<UserStringProperty InitialValue="Lowpass" Name="FilterType" Options="Lowpass|Highpass"/>
	</Properties>
```

The options must be separated with "|". The initial value must be one of the options.  

The tag "Code" contains informations for the execution of the component, in the simulation and in the synthesizer. The tag "Simulation" is created by the Atomic Builder. The tag "Device" contains the information that are used by the Soundgates synthesizer. It can have one or two "Implementation" tags. Such a tag stands either for the software implementation (indicated by the type "sw") or for the hardware implementation (indicated by the type "hw"). An implementation has the attribute "Name" which is the internal name of the component and is used e.g. in the TGF file.

```	
	<Code>
		<Simulation/>
		<Device>
			<Implementation type="sw" name="fir">
				<PortMapping PortName="SoundIn" PortNumber="1"/>
				<PortMapping PortName="CutoffFrequency" PortNumber="2"/>
				<PortMapping PortName="SoundOut" PortNumber="1"/>
			</Implementation>
			<Implementation type="hw" name="fir">
				<PortMapping PortName="SoundIn" PortNumber="1"/>
				<PortMapping PortName="CutoffFrequency" PortNumber="2"/>
				<PortMapping PortName="SoundOut" PortNumber="1"/>
			</Implementation>
		</Device>
	</Code>
```	

Each implementation has a list of port mappings which are used for the descriptions of connections in the TGF file. Each tag "PortMapping" has the attributes "PortName" and "PortNumber". The in-ports and the out-ports are numbered separately. The numeration of ports starts with 1.  

**IMPORTANT: For each port of the component there must exist a port mapping!"**
