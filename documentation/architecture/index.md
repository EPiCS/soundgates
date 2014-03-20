---
title: Synthesizer Architecture
layout: page
---
# Synthesizer Architecture


## Introduction

The _Synthesizer_ is a dynamic data flow graph processing engine. The Synthesizer is designed as a framework such that it can be extended by dsp plugins (e.g. filters, oscillators, arithmetic components and so on). The framework also  provides some internal interfaces where it can be extended by other developers (e.g. user interfaces services).

In the following the parts of the framework will explained in more detail.

## SoundComponent, Links and Ports

A `SoundComponent` is a proxy object that delegates interface calls to its actual implementation. The implementation subclasses `SoundComponentImpl`. A sound component is a node in a data flow graph. Sound components are connected by _Links_ whichs subclass the `Link` class. Currently there are two different types of links: a `ControlLink` and a `SoundLink`. A sound link stores a fixed number of pcm coded samples inside an internal buffer such that they can be accessed by other sound components. A control link stores a floating point control value.

The internal value of a link can be accessed through ports. For every type of link exists a corresponding port type: a `SoundPort` and a `ControlPort`. Control values can be updated asynchronously. To get a notification whether a control value was updated the plugin developer can register a callback functor at the port of interest. There exist some common callback functor classes that can be used.

## User interface services

A user interface service or `UIService` is an interface for services that communicate with the user. There already exist a service to propagate IO osc addresses to mobile devices (`TCPHandshareService`), a service to receive osc conform messages (`OSCService`) and an xml-rpc service (`RPCService`, currently unused). The `UIManager` singleton is used to manage the lifetime of UIServices. Services can be registered, started and stopped. When the Synthesizer is closed all UIServices will be stopped and destroyed;

## Loading patches and sound components

Sound component plugins are managed by the `SoundComponentLoader` singleton. It is responsible for discovering plugins, loading plugins in a given folder structure and creating instances of it according to the input file.

The layout of a patch, its nodes and their connections is described by a .tgf-file. The tgf file is read by a `TGFFileReader`. It is possible add other file formats by subclassing the `SynthesizerFileReader`.

## Patch

A Patch object is the main hub for all sound components and the links between them. A patch object also contains the logic to run, stop and dispose a patch.

## SoundgatesConfig

The `SoundgatesConfig` is an interface to the configuration values of the Synthesizer. It is a wrapper around ` boost::property_tree`, therefore it can be easily extended to other file formats and properties. A value in the configuration is accessed by the enumaration value via `SoundgatesConfig::getInstance().get<datatype>(ENUM)`, which is sould be defined in the `SoundgatesConfig`-header file. The search path for configuration files is relative to the Synthesizer: `.\config\config.xml`.





