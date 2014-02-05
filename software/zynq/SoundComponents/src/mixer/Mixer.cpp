/*
 * Mixer.cpp
 *
 *  Created on: Jan 2, 2014
 *      Author: lukas
 */

#include "Mixer.h"

#include "impl/MixerSW.h"

DEFINE_COMPONENTNAME(Mixer, "mixer");

EXPORT_SOUNDCOMPONENT_SW_ONLY(Mixer);

Mixer::Mixer(std::vector<std::string> params) : SoundComponentImpl(params) {

	m_bias = 0.0f;

    CREATE_AND_REGISTER_PORT3(Mixer, In, SoundPort, SoundIn, 1);
    CREATE_AND_REGISTER_PORT3(Mixer, In, SoundPort, SoundIn, 2);

    CREATE_AND_REGISTER_PORT3(Mixer, In, ControlPort, BiasIn, 3);

    CREATE_AND_REGISTER_PORT3(Mixer, Out, SoundPort, SoundOut, 1);
}

Mixer::~Mixer() {

}

