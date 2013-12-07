/*
 * NoiseSoundComponent.h
 *
 *  Created on: Dec 7, 2013
 *      Author: lukas
 */

#ifndef NOISESOUNDCOMPONENT_H_
#define NOISESOUNDCOMPONENT_H_

#include <string.h>
#include <vector>

#include "../SoundPort.h"
#include "../Synthesizer.h"
#include "../BufferedLink.h"
#include "../SoundComponentImpl.h"

class NoiseSoundComponent: public SoundComponentImpl {

protected:

	SoundPort* m_SoundOutport;

public:

	static int soundoutport;
	static const char* name;

	NoiseSoundComponent(){

		m_SoundOutport = NULL;

	};
	NoiseSoundComponent(std::vector<std::string> params);
	virtual ~NoiseSoundComponent();

	virtual void init(void) = 0;
	virtual void process(void) = 0;
};

#endif /* NOISESOUNDCOMPONENT_H_ */
