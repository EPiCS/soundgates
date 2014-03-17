---
title: Synthesizer Components
layout: componentlist
check: "&#10003;"
uncheck: "&#10007;"
---
# Synthesizer Components
This is a list of all components that were already implemented. 
If you want to learn how to implement a component of your own, please refer to [Creation of sound components](/tutorials/createcomponent).

## Component list

Ports displayed **bold** are Sound ports, others are Control ports. 

Hardware implementation do not exist for all components, especially for most of the control components, a hardware implementation would be rather unfeasible.

All control values are of datatype float. If the control value is used for boolean functions, then a value of `0.0` is interpreted as `false`, everything else as `true`.

### Arithmetic

| **Component** | **Internal&nbsp;name** | **Input&nbsp;Ports** | **Output&nbsp;Ports** | **Properties** | **HW** | **Description** |
| ------------- | ----------------- | ----- | ---------------- | -------------- | --------------------- | ---------------------|
| Constant | const | - | - | <ol><li>Value</li></ol> | {{page.uncheck}} | Constant control value |
| Control Addition | addcc | <ol><li>Summand 1</li><li>Summand 2</li></ol> | <ol><li>Result</li></ol> | - | ? | Adds two control values |
| Control Division | divcc | <ol><li>Dividend</li><li>Divisor</li></ol> | <ol><li>Quotient</li></ol> | - | ? | Divides *Dividend* by *Divisor* |
| Control Multiplication | mulcc | <ol><li>Multiplicand</li><li>Multiplier</li></ol> | <ol><li>Product</li></ol> | - | ? | Multiplies *Multiplicand* with *Multiplier* |
| Control Subtraction | subcc | <ol><li>Minuend</li><li>Subtrahend</li></ol> | <ol><li>Difference</li></ol> | - | ? | Subtracts *Subtrahend* from *Minuend* |
| Number | ?? | TODO XML Beshreibung für Komponente namens Number vorhanden. Was soll das für eine Komponente sein? | op | p | hw | d |

### Effect

| **Component** | **Internal&nbsp;name** | **Input&nbsp;Ports** | **Output&nbsp;Ports** | **Properties** | **HW** | **Description** |
| ------------- | ----------------- | ----- | ---------------- | -------------- | --------------------- | ---------------------|
| Delay | delay | <ol><li>Delay</li><li class="sound">SoundIn</li></ol> | <ol><li class="sound">SoundOut</li></ol> | - | ? | Delays an incoming sound signal by *Delay* seconds |
| Pulse Width Modulation | pwm | <ol><li class="sound">SoundIn1</li><li class="sound">SoundIn2</li></ol> | <ol><li class="sound">SoundOut</li></ol> | - | {{page.check}} | Compares both incoming sounds sample and returns a sample with maximum amplitude if the first is larger then the other or a sample with minimum amplitude otherwise |


### Envelope

| **Component** | **Internal&nbsp;name** | **Input&nbsp;Ports** | **Output&nbsp;Ports** | **Properties** | **HW** | **Description** |
| ------------- | ----------------- | ----- | ---------------- | -------------- | --------------------- | ---------------------|
| ADSR Envelope | adsr | <ol><li class="sound">SoundIn</li><li>Attack</li><li>Decay</li><li>Sustain</li><li>Release</li><li>Trigger</ol> |<ol><li>SoundOut</li></ol> | - | {{page.check}} | Modifies the amplitude of an incoming sound signal. Once *trigger* receives a 1, the sound signal goes from 0% to 100% amplitude over *attack* seconds, falls over *decay* seconds to stays at *sustain* % as long as *trigger* is still 1 and fades out to 0% over *release* seconds |
| c | i | ip | op | p | hw | d |

### Filter

| **Component** | **Internal&nbsp;name** | **Input&nbsp;Ports** | **Output&nbsp;Ports** | **Properties** | **HW** | **Description** |
| ------------- | ----------------- | ----- | ---------------- | -------------- | --------------------- | ---------------------|
| FIR | fir | <ol><li class="sound">SoundIn</li><li>Cutoff Frequency</li></ol> | <ol><li class="sound">SoundOut</li></ol> | <ol><li>Filter Type</li></ol> | {{page.check}} | A FIR filter implementation. *Filter Type* can be set to *Lowpass* or *Highpass* |

### General

| **Component** | **Internal&nbsp;name** | **Input&nbsp;Ports** | **Output&nbsp;Ports** | **Properties** | **HW** | **Description** |
| ------------- | ----------------- | ----- | ---------------- | -------------- | --------------------- | ---------------------|
| Mixer | mixer | <ol><li class="sound">Sound 1</li><li class="sound">Sound 2</li><li>Bias</li></ol> | <ol><li class="sound">Output</li></ol> | - | {{page.check}} | Mixes together two waveforms, weighed by *Bias* |


### Logic

| **Component** | **Internal&nbsp;name** | **Input&nbsp;Ports** | **Output&nbsp;Ports** | **Properties** | **HW** | **Description** |
| ------------- | ----------------- | ----- | ---------------- | -------------- | --------------------- | ---------------------|
| Accumulator	| accumulatorcc	| <ol><li>AccIn</li><li>Reset</li></ol> | <ol><li>Out</li></ol> | - | {{page.uncheck}} | Accumulates incoming control values and pushes them to the output. |
| AND		| and		| <ol><li>Value 1</li><li>Value 2</li></ol> | <ol><li>Result</li></ol> | - | {{page.uncheck}} | Boolean AND |
| Demultiplexer | demux | <ol><li>Data</li><li>Selector</li></ol> | <ol><li>Data 1</li><li>Data 2</li></ol> | - | ? | Puses the incoming *Data* to one of the outputs, depending on whether *Selector* is `true` or `false` |
| Multiplexer | mux | TODO Zum MUX gibt es noch keine XML Beschreibung im Editor! | op | p | hw | d |
| NOT | not | <ol><li>Value</li></ol> | <ol><li>Negation</li></op> | - | {{page.uncheck}} | Logical NOT |
| OR	| or		| <ol><li>Value 1</li><li>Value 2</li></ol> | <ol><li>Result</li></ol> | - | {{page.uncheck}} | Boolean OR |
| c | i | ip | op | p | hw | d |


### OSC

| **Component** | **Internal&nbsp;name** | **Input&nbsp;Ports** | **Output&nbsp;Ports** | **Properties** | **HW** | **Description** |
| ------------- | ----------------- | ----- | ---------------- | -------------- | --------------------- | ---------------------|
| IO | input | - | <ol><li>Value</li></ol> | <ol><li>TODO Name, Range... abweichendes Format</li></ol> | {{page.uncheck}} | TODO |


### Sink
| **Component** | **Internal&nbsp;name** | **Input&nbsp;Ports** | **Output&nbsp;Ports** | **Properties** | **HW** | **Description** |
| ------------- | ----------------- | ----- | ---------------- | -------------- | --------------------- | ---------------------|
| Logger | log | <ol><li class="sound">Sound</li><li>Trigger</li></ol> | - | <ol><li>Logging Prefix</li><li>Filename</li></ol> | {{page.uncheck}} | Logs all samples to console and to a file, as long as *Trigger* is `true`, preceeded by *Logging Prefix*. It is usually better to use the more sophisticated logging framework as described at !TODO HIER LINK ZUM LOGGING! |

### Source
| **Component** | **Internal&nbsp;name** | **Input&nbsp;Ports** | **Output&nbsp;Ports** | **Properties** | **HW** | **Description** |
| ------------- | ----------------- | ----- | ---------------- | -------------- | --------------------- | ---------------------|
| White Noise | noise | - | <ol><li class="sound">Samples</li></ol> | - | {{page.check}} | Generates White noise |



