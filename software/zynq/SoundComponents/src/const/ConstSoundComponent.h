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

#include <ControlLink.h>
#include <ControlPort.h>
#include <Synthesizer.h>
#include <SoundComponentImpl.h>

#include <boost/lexical_cast.hpp>

class ConstSoundComponent: public SoundComponentImpl {

private:
	float		 m_CtrlData;
public:


	DECLARE_COMPONENTNAME
	DECLARE_PORT3(ControlPort, CtrlOut, 1);

	ConstSoundComponent(std::vector<std::string> params);
	virtual ~ConstSoundComponent();

	void init(void);
	void process(void);
	void initLater();
};

#endif /* NOISESOUNDCOMPONENT_H_ */
