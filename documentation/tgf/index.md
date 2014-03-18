---
title: Trivial Graph Format
layout: page
---

The Synthesizer uses the Trivial Graph Format to construct a patch from sound components. Users will not want to write more complex TGF files by hand, but use the editor to generate them.
The format consists of node and edge definitions.
### Node syntax
`<uid> <component>/<impltype>/'parameter1','paramter2',...,'parameterN'`

### Edge syntax
`<source uid> <destination uid> '<outgoing port>, <incoming port>'`

### Example
```
# Nodes
# Constant with initial value 3.0
1 const/sw/'3.0'
# Note that a component must not end with a / if it has no parameters like this sine generator
2 sin/sw
# Hardware sine generator in HW-Slot 0
3 sin/hw(0)
4 adsr/sw
5 mixer/hw(1)
6 waveplayer/sw
# Input node. The first parameter '/BeatLevel f' defines an OSC address with datatype float, the second parameter defines the range of this input node.
7 input/sw/'/BeatLevel f','[0:800]'
8 input/sw/'/Trigger i','[0:1]'
9 const/sw/'0.1'
10 const/sw/'0.1'
11 const/sw/'0.7'
12 const/sw/'0.1'
13 output/sw/'left'

# Edges
1 2 '1,1'
2 3 '1,1'
3 4 '1,1'
8 4 '1,1'
9 4 '1,2'
10 4 '1,3'
11 4 '1,4'
12 4 '1,5'
4 5 '1,1'
6 5 '1,2'
7 5 '1,3'
5 13 '1,1'
```
