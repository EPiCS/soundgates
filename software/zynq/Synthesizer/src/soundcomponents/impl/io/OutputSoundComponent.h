/*
 * OutputSoundComponent.h
 *
 *  Created on: Nov 30, 2013
 *      Author: gwue
 *
 * This node encapsulates the Alsa Interace.
 * It reads data from the first input port and pushes it to the soundcard
 */

#ifndef OUTPUTSOUNDCOMPONENT_H_
#define OUTPUTSOUNDCOMPONENT_H_

#include <string>
#include <vector>

#include "../../SoundComponentImpl.h"
extern "C" {
#include "Samplebuffer.h"
}

// TODO Remove these defines when merged with the other project
#ifndef SAMPLE_SIZE
#define SAMPLE_SIZE        sizeof(int)
#endif

#ifndef SAMPLE_RATE
#define SAMPLE_RATE        44100
#endif

#ifndef SAMPLE_COUNT
#define SAMPLE_COUNT       64
#endif


class OutputSoundComponent: public SoundComponentImpl {

private:
	soundbuffer* buffer;

public:
	static int soundInPort;

	OutputSoundComponent();
	~OutputSoundComponent();

	void init(void);
	void process(void);

};

#endif /* OUTPUTSOUNDCOMPONENT_H_ */
