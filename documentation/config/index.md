---
title: Configuration
layout: page
---

Several parameters of the Synthesizer program can be configured. There must exists a folder *config* within the same directory as the Synthesizer with a *config.xml* inside.
An exemplary config file with default values is shown below. Parts of the config file may be omitted and these defaults will be used.

```
<synthesizer>
    <alsa>
        <buffersize>16384</buffersize>
        <chunks>1024</chunks>
        <devicename>plughw:0,0</devicename>
        <devicename_in>plughw:0,0</devicename_in>
    </alsa>
    
    <core>
        <hwt_support>false</hwt_support>
        <ports>
            <tcp>50050</tcp>
            <udp>50050</udp>
        </ports>
    </core>
    
    <plugins>
        <searchdir>./plugin</searchdir>
    </plugins>
    
    <logging>
    	<enabled>false</enabled>
    	<initDelay>1</initDelay>
    	<stopAfter>1000</stopAfter>
    	<address>localhost</address>
    	<port>1338</port>
    </logging>    
</synthesizer>
```

1. The **alsa** section defines parameters how the ALSA soundsystem should operate:
 - *buffersize*: Amount of memory ALSA should internally use for its buffer. Usually, there is no need to change this.
 - *chunks*: Numer of sound samples the Synthesizer should generate before delegating them to the ALSA subsystem. Higher values might increase delay, too low values will lead to hearable artifacts in playback.
 - *devicename*: Output device ALSA should use. *plughw:0,0* should work on most machines that only have one soundcard with one device. `aplay -l` will list your available devices.
 - *devicename_in*: Same as *devicename*, only for recording. Usually the same vale as for *devicename*, but might vary on different machines.
2. **core**:
 - *hwt_support*: Components may run in Hardware if there is an FPGA implementation available. If this is set to false, components marked for hardware will run in software regardless. At the moment, hardware support in general only exists for the arm platform. If you intend to run on an x86_64 machine, you have to set this to false.
 - *ports/tcp*: Handshake port that is exposed to the Android app
 - *ports/upd*: Data port that on which to listen for the Android apps data. Should be the same as the TCP port at the moment
3. **plugins**:
 - *searchdir*: The folder where the shared libraries of sound components reside. Paths are relative to the Synthesizer executable. Absolute paths work as well, of course.
4. **logging**:
 - *enabled*: Should logging be enabled at all. Logging will considerably affect playback, so only activate this for debug purpose.
 - *initDelay*: An initial delay in milliseconds the Synthesizer will wait before producing any samples. Useful if you need to set input values before logging.
 - *stopAfter*: Milliseconds after which the Synthesizer program will exit. When logging, you should only log small amounts of time, since the BSON format can only handle limited amounts of data.
 - *address*: Address at which the database backend runs
 - *port*: Port at which the database backend runs
