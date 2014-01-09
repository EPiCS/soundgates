/*
 * SamplePlayer.cpp
 *
 *  Created on: Dec 28, 2013
 *      Author: lukas
 */

#include "DummySink.h"

/* Define inports */
DEFINE_PORT(DummySink, SoundIn, 1);

DEFINE_COMPONENTNAME(DummySink, "dummysink")

EXPORT_SOUNDCOMPONENT_NO_IMPL( DummySink );

DummySink::DummySink(std::vector<std::string> params) : SoundComponentImpl(params) {

    CREATE_AND_REGISTER_PORT(DummySink, In, SoundPort, SoundIn);
}

DummySink::~DummySink() { }


