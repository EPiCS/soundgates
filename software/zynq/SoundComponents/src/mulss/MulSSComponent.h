/*
 * MulSSComponentComponent.h
 *
 *  Created on: Dec 6, 2013
 *      Author: lukas
 */

#ifndef MulSSComponent_H_
#define MulSSComponent_H_

#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>

class MulSSComponent: public SoundComponentImpl {

public:

	DECLARE_COMPONENTNAME

	DECLARE_PORT3(SoundPort, SoundIn, 1);
	DECLARE_PORT3(SoundPort, SoundIn, 2);

	DECLARE_PORT3(SoundPort, SoundOut, 1);

	MulSSComponent(std::vector<std::string> params);
	virtual ~MulSSComponent();

	void init(void);
	void process(void);

};

#endif /* MulSSComponent */
