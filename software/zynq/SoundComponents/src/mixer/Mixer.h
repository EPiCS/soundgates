/*
 * Mixer.h
 *
 *  Created on: Jan 2, 2014
 *      Author: lukas
 */

#ifndef MIXER_H_
#define MIXER_H_

#include <sstream>

#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>

#define MIXER_MAX_PORT_NUMBER 10

class Mixer : SoundComponentImpl {

public:

    DECLARE_COMPONENTNAME;

    DECLARE_PORT3(SoundPort, SoundIn, 1);
    DECLARE_PORT3(SoundPort, SoundIn, 2);
    DECLARE_PORT3(SoundPort, SoundIn, 3);
    DECLARE_PORT3(SoundPort, SoundIn, 4);
    DECLARE_PORT3(SoundPort, SoundIn, 5);
    DECLARE_PORT3(SoundPort, SoundIn, 6);
    DECLARE_PORT3(SoundPort, SoundIn, 7);
    DECLARE_PORT3(SoundPort, SoundIn, 8);
    DECLARE_PORT3(SoundPort, SoundIn, 9);
    DECLARE_PORT3(SoundPort, SoundIn, 10);

    DECLARE_PORT3(ControlPort, BiasIn, 11);
    DECLARE_PORT3(ControlPort, BiasIn, 12);
    DECLARE_PORT3(ControlPort, BiasIn, 13);
    DECLARE_PORT3(ControlPort, BiasIn, 14);
    DECLARE_PORT3(ControlPort, BiasIn, 15);
    DECLARE_PORT3(ControlPort, BiasIn, 16);
    DECLARE_PORT3(ControlPort, BiasIn, 17);
    DECLARE_PORT3(ControlPort, BiasIn, 18);
    DECLARE_PORT3(ControlPort, BiasIn, 19);
    DECLARE_PORT3(ControlPort, BiasIn, 20);

    DECLARE_PORT2(SoundPort, SoundOut, 1);

    int m_nPorts;

    Mixer(std::vector<std::string> params);
    virtual ~Mixer();
};

#endif /* MIXER_H_ */
