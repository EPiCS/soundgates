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
        stringstream ( params[0] ) >> m_nPorts;
    }else{

        m_nPorts = 0;
    }

    CREATE_AND_REGISTER_PORT2(Mixer, In, SoundPort, SoundIn, 1);
    CREATE_AND_REGISTER_PORT2(Mixer, In, SoundPort, SoundIn, 2);
    CREATE_AND_REGISTER_PORT2(Mixer, In, SoundPort, SoundIn, 3);
    CREATE_AND_REGISTER_PORT2(Mixer, In, SoundPort, SoundIn, 4);
    CREATE_AND_REGISTER_PORT2(Mixer, In, SoundPort, SoundIn, 5);
    CREATE_AND_REGISTER_PORT2(Mixer, In, SoundPort, SoundIn, 6);
    CREATE_AND_REGISTER_PORT2(Mixer, In, SoundPort, SoundIn, 7);
    CREATE_AND_REGISTER_PORT2(Mixer, In, SoundPort, SoundIn, 8);
    CREATE_AND_REGISTER_PORT2(Mixer, In, SoundPort, SoundIn, 9);
    CREATE_AND_REGISTER_PORT2(Mixer, In, SoundPort, SoundIn, 10);

    CREATE_AND_REGISTER_PORT2(Mixer, Out, SoundPort, SoundOut, 1);
}

Mixer::~Mixer() {

}

