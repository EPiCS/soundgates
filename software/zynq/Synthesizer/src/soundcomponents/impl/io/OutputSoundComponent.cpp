/*
 * OutputSoundComponent.cpp
 *
 *  Created on: Nov 30, 2013
 *      Author: gwue
 */

#include "OutputSoundComponent.h"

int OutputSoundComponent::soundInPort = 1;

OutputSoundComponent::OutputSoundComponent() {
	buffer = buffer_initialize(44100, 0);

	std::vector<Port>& inports = getInports();
	Port soundInPort = Port(OutputSoundComponent::soundInPort);
	inports.push_back(soundInPort);
}

OutputSoundComponent::~OutputSoundComponent() {
}

void OutputSoundComponent::process() {
	Port* soundInPort = SoundComponentImpl::getIncomingPort(1);

	while (!buffer_needsamples(this->buffer)) {
		usleep(100);
	}

	char* bufferArray = soundInPort->getBufferedLink()->getReadBuffer();
	int bufferSize = soundInPort->getBufferedLink()->getBufferDepth();

	buffer_fillbuffer(this->buffer, bufferArray, bufferSize);
}
