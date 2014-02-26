/*
 * AccumulatorCC.hpp
 *
 *  Created on: Feb 25, 2014
 *      Author: posewsky
 */

#ifndef ACCUMULATORCC_HPP_
#define ACCUMULATORCC_HPP_

#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>
#include <Listener.h>

class AccumulatorCC: public SoundComponentImpl
{

public:

	float value;

	DECLARE_COMPONENTNAME

	DECLARE_PORT3(ControlPort, AccIn, 1);
	DECLARE_PORT3(ControlPort, Reset, 2);

	DECLARE_PORT3(ControlPort, Out, 1);

	AccumulatorCC(std::vector<std::string> params);
	virtual ~AccumulatorCC();

	void init(void);
	void process(void);

};

#endif
