/*
 * SoundAddComponent.cpp
 *
 *  Created on: Dec 6, 2013
 *      Author: lukas
 */

#include "SoundAddComponent.h"


DEFINE_COMPONENTNAME(SoundAddComponent, "addss");

EXPORT_SOUNDCOMPONENT_NO_IMPL(SoundAddComponent);

SoundAddComponent::SoundAddComponent(std::vector<std::string> params) : SoundComponentImpl(params) {

    CREATE_AND_REGISTER_PORT3(SoundAddComponent, In, SoundPort, SoundIn, 1);

    CREATE_AND_REGISTER_PORT3(SoundAddComponent, In, SoundPort, SoundIn, 2);

    CREATE_AND_REGISTER_PORT3(SoundAddComponent, Out, SoundPort, SoundOut, 1);
}

SoundAddComponent::~SoundAddComponent() { }


void SoundAddComponent::init(void){ /* do nothing */ }

void SoundAddComponent::process(void){

    int sample;

	for(int i = 0; i < Synthesizer::config::blocksize; i++){
	    // Fixme: fixed point addition ohne bereichsüberläufe

	    /*float a = (*m_SoundIn_1_Port)[i];
	    float b = (*m_SoundIn_2_Port)[i];*/

		bool overflow = false;
		bool sameSide = false;
		bool positive = true;

		int s1 = (*m_SoundIn_1_Port)[i];
		int s2 = (*m_SoundIn_2_Port)[i];

		if (s1 >= 0 && s2 >= 0)
		{
			sameSide = true;
			positive = true;
		}
		else if (s1 <= 0 && s2 <= 0)
		{
			sameSide = true;
			positive = false;
		}

	    sample = s1 + s2;

	    // Positive overflow -> set to max
	    if (positive && sameSide && sample < 0) {
	    	sample = INT_MAX;
	    }
	    // negative overflow
	    else if (!positive && sameSide && sample > 0) {
	    	sample = INT_MIN;
	    }

	    /*if (sample > (1 << 30)){
	        sample = (1 << 30);
	    }

	    if(sample < (- (1 << 30))){
	        sample = -(1 << 30);
	    }*/

	    m_SoundOut_1_Port->writeSample(sample, i);

	}
}
