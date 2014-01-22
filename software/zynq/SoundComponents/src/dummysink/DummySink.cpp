/*
 * SamplePlayer.cpp
 *
 *  Created on: Dec 28, 2013
 *      Author: lukas
 */

#include "DummySink.h"

/* Define inports */
DEFINE_COMPONENTNAME(DummySink, "dummysink")

EXPORT_SOUNDCOMPONENT_NO_IMPL( DummySink );

DummySink::DummySink(std::vector<std::string> params) : SoundComponentImpl(params) {

    CREATE_AND_REGISTER_PORT3(DummySink, In, SoundPort, SoundIn, 1);
}

DummySink::~DummySink() { }


