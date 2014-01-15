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

    if(params.size() == 1){
        std::stringstream ( params[0] ) >> m_nPorts;
    }else{
        m_nPorts = 0;
    }

    CREATE_AND_REGISTER_PORT3(Mixer, In, SoundPort, SoundIn, 1);
    CREATE_AND_REGISTER_PORT3(Mixer, In, SoundPort, SoundIn, 2);
    CREATE_AND_REGISTER_PORT3(Mixer, In, SoundPort, SoundIn, 3);
    CREATE_AND_REGISTER_PORT3(Mixer, In, SoundPort, SoundIn, 4);
    CREATE_AND_REGISTER_PORT3(Mixer, In, SoundPort, SoundIn, 5);
    CREATE_AND_REGISTER_PORT3(Mixer, In, SoundPort, SoundIn, 6);
    CREATE_AND_REGISTER_PORT3(Mixer, In, SoundPort, SoundIn, 7);
    CREATE_AND_REGISTER_PORT3(Mixer, In, SoundPort, SoundIn, 8);
    CREATE_AND_REGISTER_PORT3(Mixer, In, SoundPort, SoundIn, 9);
    CREATE_AND_REGISTER_PORT3(Mixer, In, SoundPort, SoundIn, 10);

    CREATE_AND_REGISTER_PORT3(Mixer, In, ControlPort, BiasIn, 11);
    CREATE_AND_REGISTER_PORT3(Mixer, In, ControlPort, BiasIn, 12);
    CREATE_AND_REGISTER_PORT3(Mixer, In, ControlPort, BiasIn, 13);
    CREATE_AND_REGISTER_PORT3(Mixer, In, ControlPort, BiasIn, 14);
    CREATE_AND_REGISTER_PORT3(Mixer, In, ControlPort, BiasIn, 15);
    CREATE_AND_REGISTER_PORT3(Mixer, In, ControlPort, BiasIn, 16);
    CREATE_AND_REGISTER_PORT3(Mixer, In, ControlPort, BiasIn, 17);
    CREATE_AND_REGISTER_PORT3(Mixer, In, ControlPort, BiasIn, 18);
    CREATE_AND_REGISTER_PORT3(Mixer, In, ControlPort, BiasIn, 19);
    CREATE_AND_REGISTER_PORT3(Mixer, In, ControlPort, BiasIn, 20);

    CREATE_AND_REGISTER_PORT3(Mixer, Out, SoundPort, SoundOut, 1);
}

Mixer::~Mixer() {

}

