---
title: Editor Manual
layout: page
---
# Editor Manual

The following tutorials show the functions of the two Soundgates editors on simple workflow examples.

## Creating a new Soundgates project
1. Right-click in the Eclipse project explorer and choose "New" -> "Other".

2. Find the folder "Soundgates".
![Soundgates wizards](http://pc2.github.io/pg-soundgates/assets/images/1_choose_wizard.png)

3. Choose "Soundgates Project" and click "Next". 
![New Soundgates Project](http://pc2.github.io/pg-soundgates/assets/images/2_create_project.png)

4. On the following wizard page type the name of your project, for example "MySoundgatesProject" and click on "Finish".

5. A new Soundgates project appears in your project explorer. It contains two folders: "samples" and "soundcomponents". 
**IMPORTANT: DO NOT delete or rename these folders!**
![Soundgates Project](http://pc2.github.io/pg-soundgates/assets/images/3_ready_project.png)

## Creating a Soundgates patch
1. Right-click on the Soundgates project and choose "New" -> "Other".

2. Choose "Soundgates Patch Diagram" from the folder "Soundgates" and click "Next".
![New Soundgates Patch](http://pc2.github.io/pg-soundgates/assets/images/4_new_patch.png)

3. Type the name of your patch file, for example "MyPatchDiagram.sgd" and click on "Finish".
**IMPORTANT: the file ending must be "sgd".**

4. A file with the new patch is created in your project and the editor for this patch opens. The left part is the canvas. The right part is the tool palette. 
The tool palette consists of three parts with separators between them:
--* The first part are the categories of atomic sound components, e.g. "Oscillator", "Envelope" etc..
--* The second part are the composite sound components. The only category in this part are the "Imported Composite Sound Components".
--* The third part are the connections.
![Editor workbench](http://pc2.github.io/pg-soundgates/assets/images/5_editor_with_palette.png)

## Adding sound components to the patch and connecting them
1. To add an atomic sound component to your patch, perform following steps:
--* Choose the corresponding category in the tool palette.
--* Click once on the tool entry associated with the atomic sound component.
--* Click once in the editor canvas in the point where you want to place the component.
Add the following components to your patch:
--* "SineGenerator" from the category "Oscillator".
--* "SampleControlMultiplication" from "Arithmetic".
--* "SoundOutput" from "Sink".
--* Two "IO" components from "OSC".

2. A component can have an arbitrary name in the diagram. To rename a component, perform following steps:
--* Click once on the component in the diagram. The component is marked.
--* Click once on the name of the component.
--* Type the new name.
**IMPORTANT: The IO components must have unique names.**
Rename one of the two added IO components to "/freq" and the second one to "/ampl". 
Now your patch may look like this:
![First components](http://pc2.github.io/pg-soundgates/assets/images/6_first_blocks.png)

3. The atomic sound components have static properties that can not be changed during runtime. 
To modify the properties of an atomic sound component contained in the patch, double-click on this component.
For example, if you double-click on the added Sine Generator component, you get this window:
![Implementation type](http://pc2.github.io/pg-soundgates/assets/images/7_sin.png)
Each atomic sound component has the property "Implementation". 
This property allows you to choose if the atomic sound component is implemented in software or in hardware.
The IO components have the properties "MinValue" and "MaxValue" which define the range of values this IO component can receive.
Double-click on the IO component named "/freq" and set "220" for "MinValue" and "440" for "MaxValue".
![/freq](http://pc2.github.io/pg-soundgates/assets/images/8_io_freq.png)
Double-click on the IO component named "/ampl" and set "0" for "MinValue" and "2" for "MaxValue".

4. Now the ports of the sound components must be connected. To make a connection you have two possibilities. 
The first one uses the tool palette:
--* Choose the part "Connections" in the tool palette and click once on the tool entry "Link".
--* Click on an out-port without releasing the left mouse button, drag the new link to an in-port and release the button.
The second way to create a link doesn't use the tool palette and is a bit faster:
--* Point the mouse over an out-port. A small arrow appears.
--* Drag this arrow to an in-port and release the button.
Connect the following ports:
- "Value" of "/freq" with "Frequency" of "SineGenerator1"
- "Value" of "/ampl" with "Multiplier" of "SampleControlMultiplication1"
- "Output" of "SineGenerator" with "Multiplicand" of "SampleControlMultiplication1"
- "Product" of "SampleControlMultiplication1" with "Sound" of "SoundOutput"
Your patch may look like this:
![Connected components](http://pc2.github.io/pg-soundgates/assets/images/9_blocks_connected.png)
**IMPORTANT: An out-port can have an arbitrary number of outgoing links, but an in-port can have only ONE incoming link!** 
**Only following links are allowed:**
- A link from an out-port to an in-port
- A link from a sound-port to a sound-port
- A link from a control-port to a control-port
- A link from a control-port to a sound-port

## Creating a composite sound component
There are two ways to create a composite sound component. 
You can create an empty composite sound component and fill it with components or you can create a composite sound component from an existing component structure.

### Create an empty composite sound component
1. Right-click on the Soundgates project and choose "New" -> "Other".

2. Choose "Soundgates Composite Diagram" from the folder "Soundgates" and click "Next".

3. Type the name of your patch file, for example "CombinedWaves.sgcd" and click on "Finish".
**IMPORTANT: the file ending must be "sgcd".**

4. A file with the new composite sound component is created in your project and the editor for this component opens. 
The editor looks nearly the same as the patch editor with two differences:
- The middle part of the tool palette has an additional category "Create Composite Sound Component" with two tool entries "New composite sound component" and "New Port".
- The category "Connections" has an addition tool entry "Delegation".
![Composite editor workbench](http://pc2.github.io/pg-soundgates/assets/images/11_composite_diag_palette.png)

5. Click once on the tool entry "New composite sound component" and click once in the canvas. 
A new empty composite sound component is created. 
Make the component wider and higher such that it has enough space for embedded components.
To do this, click once on the component such that it is marked. 
Click then on one of the black points on the border of the component and drag the bounds of the component.

6. To add a sound component to the composite sound component you have to perform the same steps as for adding a component to a patch.
Add the following components to "CombinedWaves":
- "SineGenerator" from "Oscillator"
- "TriangleGenerator" from "Oscillator"
- "Constant" from "Arithmetic"
- "Mixer" from "General"
Now the component "CombinedWaves" should look like this:
![CombinedWaves](http://pc2.github.io/pg-soundgates/assets/images/12_new_composite.png)