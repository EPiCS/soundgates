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

| **Component** | **Internal&nbsp;name** | **Input&nbsp;Ports** | **Output&nbsp;Ports** | **Properties** | **HW** <th colspan="2"> **Description**</th> |
| ------------- | ----------------- | ----- | ---------------- | -------------- | --------------------- | ---------------------|
| Constant | const | - | - | <ol><li>Value</li></ol> | {{page.uncheck}} <td colspan="2"> Constant control value</td> |
| Control Addition | addcc | <ol><li>Summand1</li><li>Summand2</li></ol> | <ol><li>Result</li></ol> | - | {{page.check}} <td colspan="2"> Adds two control values </td> |
| Control Division | divcc | <ol><li>Dividend</li><li>Divisor</li></ol> | <ol><li>Quotient</li></ol> | - | {{page.uncheck}} <td colspan="2"> Divides *Dividend* by *Divisor* </td> |
| Control Multiplication | mulcc | <ol><li>Multiplicand</li><li>Multiplier</li></ol> | <ol><li>Product</li></ol> | - | {{page.check}} <td colspan="2"> Multiplies *Multiplicand* with *Multiplier* </td> |
| Control Subtraction | subcc | <ol><li>Minuend</li><li>Subtrahend</li></ol> | <ol><li>Difference</li></ol> | - | {{page.check}} <td colspan="2"> Subtracts *Subtrahend* from *Minuend* </td> |
| Sample Addition | addss | <ol><li class="sound">Sound1</li><li class="sound">Sound2</li></ol> | <ol><li class="sound">Result</li></ol> | - | {{page.check}} <td colspan="2"> Adds two soundwave samplewise </td> |
| Sample Control Addition | addsc | <ol><li class="sound">Sound1</li><li>Summand</li></ol> | <ol><li class="sound">Result</li></ol> | - | {{page.uncheck}} <td colspan="2"> Adds a control value to each sound sample </td> |
| Sample Control Multiplication | mulsc | <ol><li class="sound">Sound1</li><li>Multiplier</li></ol> | <ol><li class="sound">Result</li></ol> | - | {{page.uncheck}} <td colspan="2"> Multiplies a control value witch each sound sample </td> |
| Sample Control Addition | subsc | <ol><li class="sound">Sound1</li><li>Subtrahend</li></ol> | <ol><li class="sound">Result</li></ol> | - | {{page.uncheck}} <td colspan="2"> Subtracts a control value from each sound sample </td> |
| Sample Multiplication | mulss | <ol><li class="sound">Sound1</li><li class="sound">Sound2</li></ol> | <ol><li class="sound">Result</li></ol> | - | {{page.check}} <td colspan="2"> Multiplies two soundwave samplewise </td> |
| Sample Subtraction | subss | <ol><li class="sound">Sound1</li><li class="sound">Sound2</li></ol> | <ol><li class="sound">Result</li></ol> | - | {{page.check}} <td colspan="2"> Subtracts two soundwave samplewise </td> |

### Control

| **Component** | **Internal&nbsp;name** | **Input&nbsp;Ports** | **Output&nbsp;Ports** | **Properties** | **HW** <th colspan="2"> **Description**</th> |
| ------------- | ----------------- | ----- | ---------------- | -------------- | --------------------- | ---------------------|
| Sound2Control | soundtocontrol | <ol><li>Lower</li><li>Upper</li><li class="sound">SoundIn</li></ol> | <ol><li>ControlOut</li></ol> | - | {{page.uncheck}} <td colspan="2"> Calculates the average value of an incoming soundwave and maps this value to a range between *Lower* and *Upper* </td> |
| Timer | timer  | <ol><li>TriggerIn</li><li>Loop</li><li>Delay</li></ol> | <ol><li>TriggerOut</li></ol> | - | {{page.uncheck}} <td colspan="2"> Will send out a true after *delay* when triggered, or peridocally every *delay* seconds whenn *loop* is true. </td>|





### Effect

| **Component** | **Internal&nbsp;name** | **Input&nbsp;Ports** | **Output&nbsp;Ports** | **Properties** | **HW** <th colspan="2"> **Description**</th> |
| ------------- | ----------------- | ----- | ---------------- | -------------- | --------------------- | ---------------------|
| Delay | delay | <ol><li>Delay</li><li class="sound">SoundIn</li></ol> | <ol><li class="sound">SoundOut</li></ol> | - | {{page.uncheck}} <td colspan="2"> Delays an incoming sound signal by *Delay* seconds </td> |
| Pulse Width Modulation | pwm | <ol><li class="sound">SoundIn1</li><li class="sound">SoundIn2</li></ol> | <ol><li class="sound">SoundOut</li></ol> | - | {{page.check}} <td colspan="2"> Compares both incoming sounds sample and returns a sample with maximum amplitude if the first is larger then the other or a sample with minimum amplitude otherwise </td> |


### Envelope

| **Component** | **Internal&nbsp;name** | **Input&nbsp;Ports** | **Output&nbsp;Ports** | **Properties** | **HW** <th colspan="2"> **Description**</th> |
| ------------- | ----------------- | ----- | ---------------- | -------------- | --------------------- | ---------------------|
| ADSR Envelope | adsr | <ol><li class="sound">SoundIn</li><li>Attack</li><li>Decay</li><li>Sustain</li><li>Release</li><li>Trigger</ol> |<ol><li>SoundOut</li></ol> | - | {{page.check}} <td colspan="2"> Modifies the amplitude of an incoming sound signal. Once *trigger* receives a 1, the sound signal goes from 0% to 100% amplitude over *attack* seconds, falls over *decay* seconds to stays at *sustain* % as long as *trigger* is still 1 and fades out to 0% over *release* seconds</td> |
| Ramp Envelope | ramp | <ol><li class="sound">SamplesIn</li><li>Rising Time</li><li>Falling Time</li><li>Trigger</li></ol> | <ol><li class="sound">SamplesOut</li></ol> | - | {{page.check}} <td colspan="2"> When triggered, increases the volume from 0% to 100% over *rising time* seconds and decreases again over *falling time* seconds.</td> |

### Filter

| **Component** | **Internal&nbsp;name** | **Input&nbsp;Ports** | **Output&nbsp;Ports** | **Properties** | **HW** <th colspan="2"> **Description**</th> |
| ------------- | ----------------- | ----- | ---------------- | -------------- | --------------------- | ---------------------|
| FIR | fir | <ol><li class="sound">SoundIn</li><li>Cutoff Frequency</li></ol> | <ol><li class="sound">SoundOut</li></ol> | <ol><li>Filter Type</li></ol> | {{page.check}} <td colspan="2"> A FIR filter implementation. *Filter Type* can be set to *Lowpass* or *Highpass*</td> |

### General

| **Component** | **Internal&nbsp;name** | **Input&nbsp;Ports** | **Output&nbsp;Ports** | **Properties** | **HW** <th colspan="2"> **Description**</th> |
| ------------- | ----------------- | ----- | ---------------- | -------------- | --------------------- | ---------------------|
| Mixer | mixer | <ol><li class="sound">Sound1</li><li class="sound">Sound2</li><li>Bias</li></ol> | <ol><li class="sound">Output</li></ol> | - | {{page.uncheck}} | Mixes together two waveforms, weighed by *Bias*</td> |
| Recorder | adc | - | <ol><li class="sound">SoundOut</li></ol> | - | {{page.uncheck}} <td colspan="2"> Records sound from a microphone</td> |

### Logic

| **Component** | **Internal&nbsp;name** | **Input&nbsp;Ports** | **Output&nbsp;Ports** | **Properties** | **HW** <th colspan="2"> **Description**</th> |
| ------------- | ----------------- | ----- | ---------------- | -------------- | --------------------- | ---------------------|
| Accumulator	| accumulatorcc	| <ol><li>AccIn</li><li>Reset</li></ol> | <ol><li>Out</li></ol> | - | {{page.uncheck}} <td colspan="2"> Accumulates incoming control values and pushes them to the output.</td> |
| AND		| and		| <ol><li>Value1</li><li>Value2</li></ol> | <ol><li>Result</li></ol> | - | {{page.uncheck}} <td colspan="2"> Boolean AND</td> |
| Demultiplexer | demux | <ol><li>Data</li><li>Selector</li></ol> | <ol><li>Data1</li><li>Data2</li></ol> | - | {{page.uncheck}} <td colspan="2"> Puses the incoming *Data* to one of the outputs, depending on whether *Selector* is `true` or `false`</td> |
| Multiplexer | mux | <ol><li>MuxIn1</li><li>MuxIn2</li><li>Selector</li></ol> | <ol><li>Data</li></ol> | - | {{page.uncheck}} <td colspan="2"> Multiplexes between two incoming control signals.</td> |
| NOT | not | <ol><li>Value</li></ol> | <ol><li>Negation</li></op> | - | {{page.uncheck}} <td colspan="2"> Logical NOT</td> |
| OR	| or		| <ol><li>Value1</li><li>Value2</li></ol> | <ol><li>Result</li></ol> | - | {{page.uncheck}} <td colspan="2"> Boolean OR</td> |


### OSC

| **Component** | **Internal&nbsp;name** | **Input&nbsp;Ports** | **Output&nbsp;Ports** | **Properties** | **HW** <th colspan="2"> **Description**</th> |
| ------------- | ----------------- | ----- | ---------------- | -------------- | --------------------- | ---------------------|
| IO | input | - | <ol><li>Value</li></ol> | <ol><li>OSC Name</li><li>Value range</li></ol> | {{page.uncheck}} <td colspan="2"> IO components can be communicated with via OSC messages. They will also appear in the COSMIC android app.</td> |

### Oscillator

| **Component** | **Internal&nbsp;name** | **Input&nbsp;Ports** | **Output&nbsp;Ports** | **Properties** | **HW** <th colspan="2"> **Description**</th> |
| ------------- | ----------------- | ----- | ---------------- | -------------- | --------------------- | ---------------------|
| Sawtooth Generator | saw | <ol><li>Frequency</li></ol> | <ol><li class="sound">SoundOut</li></ol> | - | {{page.check}} <td colspan="2"> Generates a sawtooth soundwave with a given frequency</td> |
| Sine Generator | sine | <ol><li>Frequency</li></ol> | <ol><li class="sound">SoundOut</li></ol> | - | {{page.check}} <td colspan="2"> Generates a sine soundwave with a given frequency</td> |
| Square Generator | square | <ol><li>Frequency</li></ol> | <ol><li class="sound">SoundOut</li></ol> | - | {{page.check}} <td colspan="2"> Generates a square soundwave with a given frequency</td> |
| Triangle Generator | triangle | <ol><li>Frequency</li></ol> | <ol><li class="sound">SoundOut</li></ol> | - | {{page.check}} <td colspan="2"> Generates a triangle soundwave with a given frequency</td> |

### Sink

| **Component** | **Internal&nbsp;name** | **Input&nbsp;Ports** | **Output&nbsp;Ports** | **Properties** | **HW** <th colspan="2"> **Description**</th> |
| ------------- | ----------------- | ----- | ---------------- | -------------- | --------------------- | ---------------------|
| Logger | log | <ol><li class="sound">Sound</li><li>Trigger</li></ol> | - | <ol><li>Logging Prefix</li><li>Filename</li></ol> | {{page.uncheck}} <td colspan="2"> Logs all samples to console and to a file, as long as *Trigger* is `true`, preceeded by *Logging Prefix*. It is usually better to use the more sophisticated logging framework</td> |
| Sound Output | dac | <ol><li class="sound">Sound</li></ol> | - | - | {{page.uncheck}} <td colspan="2"> Plays back an incoming soundwave using ALSA</td> |

### Source

| **Component** | **Internal&nbsp;name** | **Input&nbsp;Ports** | **Output&nbsp;Ports** | **Properties** | **HW** <th colspan="2"> **Description**</th> |
| ------------- | ----------------- | ----- | ---------------- | -------------- | --------------------- | ---------------------|
| White Noise | noise | - | <ol><li class="sound">Samples</li></ol> | - | {{page.check}} <td colspan="2"> Generates White noise</td> |
| Wave Player | smplply | <ol><li>Trigger</li></ol> | <ol><li class="sound">Output</li></ol> | <ol><li>Filename</li></ol> | {{page.uncheck}} <td colspan="2"> When triggered, plays the wave file specified in the parameter. The filename has to be relative to the waves directory.</td> |
