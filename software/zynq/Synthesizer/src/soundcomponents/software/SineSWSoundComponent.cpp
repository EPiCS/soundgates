/*
 * SineSWSoundComponent.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */


#include "SineSWSoundComponent.h"


int SineSWSoundComponent::sineValueOutPort = 1;
int SineSWSoundComponent::frequencyInPort = 1;


SineSWSoundComponent::SineSWSoundComponent(){

	std::vector<Port>* inports = getInPorts();
	std::vector<Port>* outports = getOutPorts();

	/* Create incoming frequency port */
	Port frequency = Port(SineSWSoundComponent::frequencyInPort);
	/* Create outgoing value port */
	Port value = Port(SineSWSoundComponent::sineValueOutPort);

	inports->push_back(frequency);
	outports->push_back(value);

}

SineSWSoundComponent::~SineSWSoundComponent(){}


void SineSWSoundComponent::init(void){ }

void SineSWSoundComponent::process(void){

	std::cout << "Processing sine sw sound component\n";

	BufferedLink* valueLink = getOutgoingPort(SineSWSoundComponent::sineValueOutPort)->getBufferedLink();
	BufferedLink* frequencyLink = getIncomingPort(SineSWSoundComponent::frequencyInPort)->getBufferedLink();

	int* targetBuffer   = (int*)   valueLink->getWriteBuffer();
	float* sourceBuffer = (float*) frequencyLink->getReadBuffer();

	int targetBufferSize = valueLink->getBufferDepth();

	float phase_incr = 0.0;

	for(int i = 0; i < targetBufferSize / 4; i++){

		phase_incr = getPhaseIncrement(sourceBuffer[i]);

		targetBuffer[i] = sin(phase) * INT_MAX;

		phase += phase_incr;

		if (phase >= M_PI * 2)
		{
			phase -= M_PI * 2;
		}

		std::cout << targetBuffer[i];
	}
}

inline float SineSWSoundComponent::getPhaseIncrement(float frequency){

	return  (2 * M_PI / 44100.0) * frequency;

}
