/*
 * LowpassComponent.h
 *
 *  Created on: Dec 7, 2013
 *      Author: lukas
 */

#ifndef FILTER_H_
#define FILTER_H_

#include <string.h>
#include <vector>
#include <cmath>

#include "../SoundPort.h"
#include "../Synthesizer.h"
#include "../ControlPort.h"

#include "../ControlLink.h"
#include "../BufferedLink.h"
#include "../SoundComponentImpl.h"

class LowpassComponent: public SoundComponentImpl {

protected:

	SoundPort* m_SoundInport;
	SoundPort* m_SoundOutport;

	ControlPort* m_CutoffPort;

public:

	static int soundInport;
	static int cutoffPort;
	static int soundoutport;
	static const char* name;

	LowpassComponent(std::vector<std::string>);
	virtual ~LowpassComponent();

	virtual void init(void) 	= 0;
	virtual void process(void)	= 0;

};

#endif /* FILTER_H_ */
