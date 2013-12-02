/*
 * OutputSoundComponent.cpp
 *
 *  Created on: Nov 30, 2013
 *      Author: gwue
 */

#include "OutputSoundComponent.h"

int OutputSoundComponent::soundInPort = 1;

const char* OutputSoundComponent::name = "dac";

extern "C"
{
OutputSoundComponent* create(SoundComponents::ImplType impltype,
		std::vector<std::string> params)
{
	return new OutputSoundComponent(params);
}

void destroy(OutputSoundComponent* cmp)
{
	delete cmp;
}

const char* getComponentName()
{
	return OutputSoundComponent::name;
}
}

OutputSoundComponent::OutputSoundComponent()
{
}

OutputSoundComponent::OutputSoundComponent(std::vector<std::string> params) :
		SoundComponentImpl(params)
{

	std::vector<Port>& inports = getInports();
	Port soundInPort = Port(OutputSoundComponent::soundInPort);
	inports.push_back(soundInPort);
}

OutputSoundComponent::~OutputSoundComponent()
{

	buffer_stop(this->buffer);

	buffer_free(this->buffer);
}

void OutputSoundComponent::process()
{
	Port* soundInPort = SoundComponentImpl::getInport(
			OutputSoundComponent::soundInPort);

	BufferedLink* soundLink = soundInPort->getBufferedLink();

	char* bufferArray = soundLink->getReadBuffer();
	int bufferSize = soundInPort->getBufferedLink()->getBufferDepth();

//	std::cout << "Processing" << endl;

	buffer_fillbuffer(this->buffer, bufferArray, bufferSize);
	buffer_startPlayback(this->buffer);

}

void OutputSoundComponent::init()
{

	this->buffer = buffer_initialize(44100, 0);
	buffer_start(buffer, 0);

	std::cout << "Soundbuffer initialized" << std::endl;
}
