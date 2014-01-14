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

/*
 * Reads an plays audio samples in various formats
 */
class SamplePlayer : public SoundComponentImpl {

public:

    DECLARE_COMPONENTNAME;

    DECLARE_PORT2(SoundPort, SoundOut_Left, 1);
    DECLARE_PORT2(SoundPort, SoundOut_Right, 2);
    DECLARE_PORT2(ControlPort, Trigger, 1);

    SamplePlayer(std::vector<std::string> params);
    virtual ~SamplePlayer();

};

#endif /* SAMPLEPLAYER_H_ */
