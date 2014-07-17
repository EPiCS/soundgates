---
title: System
layout: page
---
# System
## Editor
![Soundgates Editor]({{ site.url }}/assets/images/system_editor.png)  
The Soundgates editor is the first step in the patch development. The user can add sound components from the XML library to the patch. For each sound component the user can decide if it will be implemented in hardware or in software. The sound components can be connected with each other via their ports. An additional editor allows creation of composite components which are hierarchical structures containing other sound components. These composite components can then be used in the patch editor as black boxes.  
A completed patch can be exported as a TGF file (included in a ZIP archive) that is read by the Soundgates synthesizer.

## Cosmic App

The Cosmic Android App is usually the last step in the Soundgates system workflow.
It is used to control the patches that are running on the synthesizer.
Therefore the first step to use the App is to connect to the synthesizer:  
<div align="center">
<img alt="Cosmic App - Connect" src="/assets/images/android_connect_fragment.png" />
</div>  
<p>Next the user needs to choose which components he wants to control and by which sensor (or other interaction method):</p>  
<div align="center">
<img alt="Cosmic App - Select" src="/assets/images/android_select_fragment.png" />
</div>  
<p>The last fragment of the App shows informations about the interaction with the system and allows to set parameters:</p>  
<div align="center">
<img alt="Cosmic App - Interact" src="/assets/images/android_interaction_fragment.png" />
</div>

## Synthesizer

The Synthesizer is a dynamic data flow graph processing engine. It is designed as a plugin architecture in order to use *sound components*. Sound components can be signal processing components (e.g. filters, arithmetic components, signal sources, envelope) as well as control objects (e.g. multiplexer, timer) and common objects like sample players. The synthesizer currently reads a *.tgf*-file which can be created using the *Soundgates Editor*. The synthesizer works on Linux x86(_64) and the Zynq-7000 FPGA architecture (ARM).
