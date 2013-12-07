/*
 * SoundAddComponent.h
 *
 *  Created on: Dec 6, 2013
 *      Author: lukas
 */

#ifndef SOUNDADDCOMPONENT_H_
#define SOUNDADDCOMPONENT_H_

#include <cassert>

#include "../Synthesizer.h"
#include "../SoundComponentImpl.h"

#include "../SoundPort.h"
#include "../ControlLink.h"
#include "../ControlPort.h"
#include "../BufferedLink.h"

class SoundAddComponent: public SoundComponentImpl {

private:

	SoundPort* 	 m_SoundInport_a; /* sound in port */
	SoundPort* 	 m_SoundInport_b; /* sound in port */
	SoundPort* 	 m_SoundOutport;/* sound out port */

	int m_Bufferdepth;


public:

	static const char* name;
	static int soundin_a;
	static int soundin_b;

	static int soundout;
	SoundAddComponent(std::vector<std::string> params);
	virtual ~SoundAddComponent();

	void init(void);
	void process(void);


};

#endif /* SOUNDADDCOMPONENT_H_ */
