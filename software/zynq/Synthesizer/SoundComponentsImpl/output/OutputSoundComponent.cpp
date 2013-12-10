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
	this->buffer = NULL;
}

OutputSoundComponent::OutputSoundComponent(std::vector<std::string> params) :
		SoundComponentImpl(params)
{
	this->buffer = NULL;

	std::vector<Port*>& inports = getInports();
	Port* soundInPort = new SoundPort(OutputSoundComponent::soundInPort);
	inports.push_back(soundInPort);
}

OutputSoundComponent::~OutputSoundComponent()
{
	this->buffer->stopThread();
	delete this->buffer;
}

void OutputSoundComponent::process()
{
	Port* soundInPort = SoundComponentImpl::getInport(OutputSoundComponent::soundInPort);

	BufferedLink* soundLink = (BufferedLink*) soundInPort->getLink();

	char* bufferArray 	= soundLink->getReadBuffer();
	int bufferSize 		= soundLink->getBufferDepth();

//	std::cout << "Processing" << endl;

	// Initializing all of the patch might take an arbitrary long time.
	// The soundbuffer thread might already be running, but we don't want to start playback unless data starts flowing
	// Therefore we call the start playback method here
	this->buffer->startPlayback();
	this->buffer->fillbuffer(bufferArray,bufferSize);

}

void OutputSoundComponent::init()
{

	this->buffer = new Soundbuffer();

	std::cout << "Soundbuffer initialized" << std::endl;
}
