/*
 * SineImpl_SW.cpp
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */

#include "SineImpl_SW.h"



SineImpl_SW::SineImpl_SW(std::vector<std::string> params) : SineSoundComponent(params){

	this->phase = 0.0;

}

void SineImpl_SW::init() { }

void SineImpl_SW::process() {

	Port* frequencyPort = getInport(SineSoundComponent::frequencyInPort);
	Port* valuePort 	= getOutport(SineSoundComponent::sineValueOutPort);

	ControlLink*  inlink  = (ControlLink*)(frequencyPort->getLink());
	BufferedLink* outlink = (BufferedLink*)(valuePort->getLink());

	int* targetBuffer 	  = (int*) outlink->getWriteBuffer();
	int  targetBufferSize = outlink->getBufferDepth();

	double phase_incr = getPhaseIncrement(440.0);
	for (int i = 0; i < targetBufferSize / 4; i++) {

		phase_incr = getPhaseIncrement(inlink->getNextControlData());

		targetBuffer[i] = sin(phase) * INT_MAX;

		phase += phase_incr;

		if (phase >= M_PI * 2) {
			phase -= M_PI * 2;
		}
	}

}

