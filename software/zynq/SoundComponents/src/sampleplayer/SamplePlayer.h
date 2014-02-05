/*
 * SamplePlayer.h
 *
 *  Created on: Dec 28, 2013
 *      Author: lukas
 */

#ifndef SAMPLEPLAYER_H_
#define SAMPLEPLAYER_H_

#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>
#include <Listener.h>

/*
 * Reads an plays audio samples in various formats
 */
class SamplePlayer : public SoundComponentImpl {

public:

    DECLARE_COMPONENTNAME;

    DECLARE_PORT3(SoundPort, SoundOut_Left, 1);
    DECLARE_PORT3(SoundPort, SoundOut_Right, 2);
    DECLARE_PORT3(ControlPort, Trigger, 1);

    int trigger;

    SamplePlayer(std::vector<std::string> params);
    virtual ~SamplePlayer();

};

#endif /* SAMPLEPLAYER_H_ */
