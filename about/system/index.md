---
title: System
layout: page
---
# System
## Editor
![Soundgates Editor](http://pc2.github.io/pg-soundgates/assets/images/system_editor.png)  
The Soundgates editor is the first step in the patch development. The user can add sound components from the XML library to the patch. For each sound component the user can decide if it will be implemented in hardware or in software. The sound components can be connected with each other via their ports. An additional editor allows creation of composite components which are hierarchical structures containing other sound components. These composite components can then be used in the patch editor as black boxes.  
A completed patch can be exported as a TGF file (included in a ZIP archive) that is read by the Soundgates synthesizer.

## Cosmic App

The Cosmic Android App is usually the last step in the Soundgates system workflow.
It is used to control the patches that are running on the synthesizer.
Therefore the first step to use the App is to connect to the synthesizer:  
<p align="center">![Cosmic App - Connect](http://pc2.github.io/pg-soundgates/assets/images/android_connect_fragment.png)  </p>
Next the user needs to choose which components he wants to control and by which sensor:  
<p align="center">![Cosmic App - Connect](http://pc2.github.io/pg-soundgates/assets/images/android_select_fragment.png)  </p>
The last fragment of the App shows informations about the interaction with the system and allows to set parameters:  
<p align="center">![Cosmic App - Connect](http://pc2.github.io/pg-soundgates/assets/images/android_interaction_fragment.png)  </p>
