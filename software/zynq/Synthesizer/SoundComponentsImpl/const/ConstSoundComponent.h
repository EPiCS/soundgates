/*
 * ConstSoundComponent.h
 *
 *  Created on: Dec 7, 2013
 *      Author: lukas
 */

#ifndef CONSTSOUNDCOMPONENT_H_
#define CONSTSOUNDCOMPONENT_H_

#include <vector>
#include <sstream>
#include <string>
#include <iostream>

#include "../ControlLink.h"
#include "../ControlPort.h"
#include "../Synthesizer.h"
#include "../SoundComponentImpl.h"

class ConstSoundComponent: public SoundComponentImpl {

protected:

	ControlPort* m_CtrlOutport;
	float		 m_CtrlData;


public:

	static int ctrloutport;
	static const char* name;

	ConstSoundComponent(std::vector<std::string> params);
	virtual ~ConstSoundComponent();

	void init(void);
	void process(void);
};

#endif /* NOISESOUNDCOMPONENT_H_ */
