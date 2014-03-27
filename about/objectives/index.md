---
title: Objectives
layout: page
---
# Objectives

Soundgates – Interactive Music Synthesis on FPGAs

This project aims at developing an interactive music synthesis system on an FPGA platform. Therefore, several of basic sound components are developed on an FPGA system. These library elements can then be assembled into a network of components that can be used for translating musical scores to sound and as building blocks for interactively controlled generative sound synthesis. The main goal of this project is to develop a prototype for such an interactive music system where an arbitrary number of users can interactively control the output sound by means of advanced sensors, e.g. the accelerator sensor in modern smartphones, light sensors and so on. 

For this goal, the following sub-goals were reached:

* Development of a library of basic components for sound generation, such as frequency generators, envelope generator, modulators, mixers, filters, delays for FPGAs as software models and as digital circuits implemented in VHDL. Integration of these components with the ReconOS reconfigurable operating system for FPGAs.

* Development of a graphical design environment that allows for assembling the basic sound generation components into complex sound generator networks that emulate analog and digital synthesizers. This editor drawed inspiration from similar tools for pure software based music synthesis, e.g. Cycling 74 MAX or Puredata. The design environment allows for translating the component network to software and hardware implementations.

* Development of a synthesizer component that allows for converting musical scores, e.g. in MIDI format, to sound using the generated synthesizer networks.

* Evaluation of sensors for capturing user interactions, e.g., smartphones.
