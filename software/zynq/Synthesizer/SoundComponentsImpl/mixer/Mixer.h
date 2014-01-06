/*
 * Mixer.h
 *
 *  Created on: Jan 2, 2014
 *      Author: lukas
 */

#ifndef MIXER_H_
#define MIXER_H_

#include "../Synthesizer.h"
#include "../SoundComponentImpl.h"

#define MIXER_MAX_PORT_NUMBER 10

class Mixer : SoundComponentImpl {

public:

    DECLARE_COMPONENTNAME;

    DECLARE_PORT2(SoundPort, SoundIn, 1);
    DECLARE_PORT2(SoundPort, SoundIn, 2);
    DECLARE_PORT2(SoundPort, SoundIn, 3);
    DECLARE_PORT2(SoundPort, SoundIn, 4);
    DECLARE_PORT2(SoundPort, SoundIn, 5);
    DECLARE_PORT2(SoundPort, SoundIn, 6);
    DECLARE_PORT2(SoundPort, SoundIn, 7);
    DECLARE_PORT2(SoundPort, SoundIn, 8);
    DECLARE_PORT2(SoundPort, SoundIn, 9);
    DECLARE_PORT2(SoundPort, SoundIn, 10);

    DECLARE_PORT2(SoundPort, SoundOut, 1);

    int m_nPorts;

    Mixer(std::vector<std::string> params);
    virtual ~Mixer();
};

#endif /* MIXER_H_ */
