/*
 * SamplePlayer.h
 *
 *  Created on: Dec 28, 2013
 *      Author: lukas
 */

#ifndef SAMPLEPLAYER_H_
#define SAMPLEPLAYER_H_

#include "../Synthesizer.h"
#include "../SoundComponentImpl.h"


/*
 * Reads an plays audio samples in various formats
 */
class SamplePlayer : public SoundComponentImpl {

public:

    DECLARE_COMPONENTNAME;

    DECLARE_PORT(SoundPort,   SoundOut_Left);
    DECLARE_PORT(SoundPort,   SoundOut_Right);
    DECLARE_PORT(ControlPort, Trigger);

    SamplePlayer(std::vector<std::string> params);
    virtual ~SamplePlayer();

};

#endif /* SAMPLEPLAYER_H_ */
