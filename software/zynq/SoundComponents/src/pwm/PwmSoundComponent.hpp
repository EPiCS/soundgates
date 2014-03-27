/*
 * PWMComponent.hpp
 *
 *  Created on: Jan 30, 2014
 *      Author:
 */

#ifndef PWMCOMPONENT_HPP_
#define PWMCOMPONENT_HPP_

#include <sstream>
#include <climits>
// necessary includes
#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>

class PwmSoundComponent: public SoundComponentImpl
{

public:
	float val;

	// REQUIRED: This macro creates a name field.
	DECLARE_COMPONENTNAME

	DECLARE_PORT3(SoundPort, PwmSoundIn, 1);
	DECLARE_PORT3(SoundPort, PwmSoundIn, 2);
	DECLARE_PORT3(SoundPort, PwmSoundOut, 1);

	// Constructor that has a parameter vector (also use this if the component has no parameters -> empty vector)
	PwmSoundComponent(std::vector<std::string> params);
	virtual ~PwmSoundComponent();

	// Initialization that is common regardless of SW/HW
	virtual void init(void);

	virtual void process(void) = 0;

};
#endif /* TEMPLATECOMPONENT_HPP_ */
