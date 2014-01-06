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
class DummySink : public SoundComponentImpl {

public:

    DECLARE_COMPONENTNAME;

    DECLARE_PORT(SoundPort, SoundIn);

    DummySink(std::vector<std::string> params);
    virtual ~DummySink();

    void init(){ /* do nothing */ };
    void process(){ /* do nothing */ };

};

#endif /* SAMPLEPLAYER_H_ */
