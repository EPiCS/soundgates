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

4. On the following wizard page enter the name of your project, for example "MySoundgatesProject" and click on "Finish".

5. A new Soundgates project appears in your project explorer. It contains two folders: "samples" and "soundcomponents".   
   **IMPORTANT: DO NOT delete or rename these folders!**  
   
   ![Soundgates Project](http://pc2.github.io/pg-soundgates/assets/images/3_ready_project.png)

## Creating a Soundgates patch
Imagine you want to have a patch where you control a sine generator with OSC messages. A sine generator has only its frequency as input. Imagine you want to control also the amplitude of the sine wave. The following tutorial shows how to create such a patch.  

1. Right-click on the Soundgates project and choose "New" -> "Other".

2. Choose "Soundgates Patch Diagram" from the folder "Soundgates" and click "Next".  

   ![New Soundgates Patch](http://pc2.github.io/pg-soundgates/assets/images/4_new_patch.png)

3. Enter the name of your patch file, for example "MyPatchDiagram.sgd" and click on "Finish".  
   **IMPORTANT: the file ending must be "sgd".**

4. A file with the new patch is created in your project and the editor for this patch opens.  
   The left part is the canvas. The right part is the tool palette.  
   The tool palette consists of three parts with separators between them:  
  * The first part are the categories of atomic sound components, e.g. "Oscillator", "Envelope" etc..
  * The second part are the composite sound components. The only category in this part are the "Imported Composite Sound Components".
  * The third part are the connections.  
  
   ![Editor workbench](http://pc2.github.io/pg-soundgates/assets/images/5_editor_with_palette.png)

## Adding sound components to the patch and connecting them
To add an atomic sound component to your patch, perform following steps:
 - Choose the corresponding category in the tool palette.
 - Click once on the tool entry associated with the atomic sound component.
 - Click once in the editor canvas in the point where you want to place the component.  
 
1. Add the following components to your patch:  
 - "SineGenerator" from the category "Oscillator".
 - "SampleControlMultiplication" from "Arithmetic".
 - "SoundOutput" from "Sink".
 - Two "IO" components from "OSC".

2. A component can have an arbitrary name in the diagram. To rename a component, perform following steps:
 - Click once on the component in the diagram. The component is marked.
 - Click once on the name of the component.
 - Enter the new name.  
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
   - Choose the part "Connections" in the tool palette and click once on the tool entry "Link".  
   - Click on an out-port without releasing the left mouse button, drag the new link to an in-port and release the button.  
   
   The second way to create a link doesn't use the tool palette and is a bit faster:  
   - Point the mouse over an out-port. A small arrow appears.  
   - Drag this arrow to an in-port and release the button.  
   
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

## Creating composite sound components
Composite sound component are hierarchical components that contain other components. They have ports as interfaces and can be used as black boxes.  
There are two ways to create a composite sound component. You can create an empty composite sound component and fill it with components or you can create a composite sound component from an existing component structure.

## Creating an empty composite sound component
Imagine you want to have a wave generator that gets a value for frequency and generates two different waves in parallel (a sine wave and a triangle wave). 
These waves are mixed together and the resulting wave is the output. To get such a mixed generator you must create a composite sound component. The following tutorial shows the steps for this creation.  

1. Right-click on the Soundgates project and choose "New" -> "Other".

2. Choose "Soundgates Composite Diagram" from the folder "Soundgates" and click "Next".

3. Enter the name of the file containing the new component, for example "CombinedWaves.sgcd" and click on "Finish".  
   **IMPORTANT: the file ending must be "sgcd".**

4. A file with the new composite sound component is created in your project and the editor for this component opens.  
   The editor looks nearly the same as the patch editor with two differences:
 - The middle part of the tool palette has an additional category "Create Composite Sound Component" with two tool entries "New composite sound component" and "New Port".
 - The category "Connections" has an additional tool entry "Delegation".  
 
   ![Composite editor workbench](http://pc2.github.io/pg-soundgates/assets/images/11_composite_diag_palette.png)

5. Click once on the tool entry "New composite sound component" and click once in the canvas.  
   A new empty composite sound component is created.  
   Rename the component to "CombinedWaves".  
   Make the component wider and higher such that it has enough space for embedded components.  
   To do this, click once on the component such that it is marked.  
   Click then on one of the black points on the border of the component and drag the bounds of the component.

## Adding elements to the composite sound component and connecting them
1. To add a sound component to the composite sound component you have to perform the same steps as for adding a component to a patch.  
   Add the following components to "CombinedWaves":
 - "SineGenerator" from "Oscillator"
 - "TriangleGenerator" from "Oscillator"
 - "Constant" from "Arithmetic"
 - "Mixer" from "General"  
   
   Set the static property "Value" of the component "Constant" to 0.5  

   **IMPORTANT: A composite sound component CAN NOT contain atomic sound components with types "IO" and/or "SoundOutput"!**   
   
2. To add a port to the composite sound component, you have to click once on the tool entry "New Port" and then click once on the component.  
   To set the port's properties you need the Eclipse Properties View. Right-click on an added port and choose "Show Properties View".  
   You can set the data type, the direction and the name of the port.  
   
   ![Port properties](http://pc2.github.io/pg-soundgates/assets/images/13_new_port.png)   
   
   Add two ports to "CombinedWaves":
 - Port "Frequency" with data type "CONTROL" and direction "IN"
 - Port "Output" with data type "SOUND" and direction "OUT"  
   
   Now the component "CombinedWaves" should look like this:  
   
   ![CombinedWaves](http://pc2.github.io/pg-soundgates/assets/images/12_new_composite.png)
   
3. Now the ports must be connected. The ports of the embedded components are connected in exactly the same way as in the patch editor.  
   Create the following links:  
   - From port "Output" of "SineGenerator1" to port "Waveform1" of "Mixer"  
   - From port "Output" of "TriangleGenerator1" to port "Waveform2" of "Mixer"  
   - From port "Value" of "Constant1" to port "Bias" of "Mixer1"
 
   To connect ports of the composite sound component with the ports of its embedded components, delegations are needed.  
   To create a delegation, use the tool entry "Delegation" and perform the same steps as for creating a link.  
   Create the following delegations:  
   - From port "Frequency" of "CombinedWaves" to port "Frequency" of "SineGenerator1"  
   - From port "Frequency" of "CombinedWaves" to port "Frequency" of "TriangleGenerator1"  
   - From port "Output" of "Mixer1" to port "Output" of "CombinedWaves"  
 
   Now the component "CombinedWaves" should look like this:  
   
   ![CombinedWaves Connected](http://pc2.github.io/pg-soundgates/assets/images/14_new_composite_connected.png)  
   
4. To use the created composite sound component in the patch editor, the component must be saved as a XML file in the folder "soundcomponents".  
   Right-click on the component and choose "Export Composite Sound Component as XML".  
   
   ![CombinedWaves Export](http://pc2.github.io/pg-soundgates/assets/images/15_composite_export.png) 
   
   You can also check the component by choosing "Validate Composite Sound Component". Before XML export the validation is done automatically.  
   In case of a successful export a new XML file is created in the folder "soundcomponents". This file contains the description of the created component.  
   
5. If you open the patch editor, the category "Imported Composite Sound Components" should contain the entry "CombinedWaves".  
   Add it to the canvas in the same way you add an atomic sound component.  
   You can not modify the embedded elements of a composite sound component in the patch editor.  
   If you don't want to see the embedded elements, then mark the component and click on the small icon under the component's name (shown in the picture). You can use the composite sound component as a black box.  
   
   ![CombinedWaves Black Box](http://pc2.github.io/pg-soundgates/assets/images/16_composite_icon.png)  
   
   ![CombinedWaves Black Box](http://pc2.github.io/pg-soundgates/assets/images/16_composite_as_black_box.png)  
   
##Creating a composite sound component from an existing component structure
Imagine you created a patch and you want to use a part of it in another patch. For example, you want to use the combination of the components SineGenerator and SampleControlMultiplication that you saw in the first tutorial.  
In this case you can create a composite sound component from these existing components. To do this, you have to perform the following steps:  

1. Mark the components and the links you want to have in the new composite sound component.  

2. Right-click and choose "Soundgates" -> "Create new Composite Sound Component".  

   ![New Composite From Existing Components](http://pc2.github.io/pg-soundgates/assets/images/17_composite_from_elements.png)  
   
3. A new composite sound component is created and saved in its own XML file in the folder "soundcomponents". The composite editor for the new component opens.     
   
   ![New Composite From Existing Components in Editor](http://pc2.github.io/pg-soundgates/assets/images/18_composite_from_elements_in_editor.png)  
   
   As you see, the ports and the delegations are created automatically. The ports derive their properties from the ports of the ports of the embedded components.  
   
4. You can edit the composite sound component (its name, ports and the embedded elements) as you saw it in the previous tutorial.  
   Rename the component to "SineGeneratorWithAmplituteControl".
   If you then export the component and place it in the patch instead of the two atomic components, the patch may look like this:  
   
   ![New Composite in Patch](http://pc2.github.io/pg-soundgates/assets/images/19_patch_with_composite.png)  
   
##Using of the component "WavePlayer" and the folder "samples"
If you want to play wave files in your patch, you must copy them into the folder "samples". 
In the patch you need the component "WavePlayer", which you find in the category "Source". 
The WavePlayer has the property "FileName" where you must enter the name of a wave file contained in the folder "samples".
For example, if have a file named "beat.wav" in the mentioned folder and you want to use it, you must enter "beat.wav" into the field "FileName" of the "WavePlayer" as shown in the picture:  

![New Composite in Patch](http://pc2.github.io/pg-soundgates/assets/images/20_wave_player.png)  

The field "FileName" expects a path relative to the folder "samples". Consider the following sub-folders in the folder "samples":  

![New Composite in Patch](http://pc2.github.io/pg-soundgates/assets/images/21_samples.png)  

For example, if you want to use the file "bassline2.wav" in a WavePlayer, you must enter "basslines/bassline2" in its field "FileName".

##Operations with a patch
![Patch operations](http://pc2.github.io/pg-soundgates/assets/images/10_patch_menu.png)  

If you have a patch, you can perform the following operations:  
- "Export Synthesizer Input" is the main operation which generates a zip file. This zip file contains the tgf file that describes the patch and the wave files that are used in the patch.
- "Validate Patch": you can check if your patch is correct. If something is wrong, error messages are shown.
- "Generate PureData Code": you can generate PureData code to simulate the patch in PureData.
- "Export Patch as XML": if you want to save your patch without using EMF or if you want to exchange your patch with other users, you can export it as a XML file. 
If you have a XML file describing a patch and you want to get an EMF model out of it such that you can edit it in the patch editor, right-click on the XML file and choose "Soundgates" -> "Create Patch from XML".
**Attention**: all the atomic and composite sound components that are used in this patch must be present in your "soundcomponents" folder!.

##Simulating a patch with PureData
If you want to simulate your patch, you have to perform the following steps:  
1. Download PureDate from http://puredata.info and install it. 
2. Choose in Eclipse "Window" -> "Preference" and click on "Pure Data" in the right menu. You get the following dialog:  
   ![PureData settings](http://pc2.github.io/pg-soundgates/assets/images/23_puredata_settings.png)  
3. Click on "Browse" and choose the file "pd.exe" from your file system. If you need parameters to start PureData (depends on the operating system you use), enter them into the field "Parameters". Click on "OK".
4. Mark the patch you want to simulate in your Soundgates project and press the green "Start the simulation" button:  
   ![Start simulation](http://pc2.github.io/pg-soundgates/assets/images/22_simulate_b.png)  
5. When the simulation is running, you can send OSC messages to the patch. Use the Soundgates app, enter your IP and the port 50050.
   You can also send OSC messages via the control panel. To do this, click on the button "Show controls".