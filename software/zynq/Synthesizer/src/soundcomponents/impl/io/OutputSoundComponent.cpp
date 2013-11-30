/*
 * OutputSoundComponent.cpp
 *
 *  Created on: Nov 30, 2013
 *      Author: gwue
 */

#include "OutputSoundComponent.h"

int OutputSoundComponent::soundInPort = 1;

OutputSoundComponent::OutputSoundComponent()
{
	buffer = buffer_initialize(44100, 0);

	std::vector<Port>& inports = getInports();
	Port soundInPort = Port(OutputSoundComponent::soundInPort);
	inports.push_back(soundInPort); //TODO SEGFAULT HERE?
}

OutputSoundComponent::~OutputSoundComponent()
{
}

void OutputSoundComponent::process()
{
	Port* soundInPort = SoundComponentImpl::getIncomingPort(1);

	char* bufferArray = soundInPort->getBufferedLink()->getReadBuffer();
	int bufferSize = soundInPort->getBufferedLink()->getBufferDepth();

	buffer_fillbuffer(this->buffer, bufferArray, bufferSize);
}

void OutputSoundComponent::init()
{
	buffer_start(this->buffer, 0);
}
