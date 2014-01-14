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

/*
 * Reads an plays audio samples in various formats
 */
class DummySink : public SoundComponentImpl {

public:

    DECLARE_COMPONENTNAME;

    DECLARE_PORT3(SoundPort, SoundIn, 1);

    DummySink(std::vector<std::string> params);
    virtual ~DummySink();

    void init(){ /* do nothing */ };
    void process(){ /* do nothing */ };

};

#endif /* SAMPLEPLAYER_H_ */
