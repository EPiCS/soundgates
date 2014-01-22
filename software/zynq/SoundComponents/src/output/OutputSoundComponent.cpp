/*
 * OutputSoundComponent.cpp
 *
 *  Created on: Nov 30, 2013
 *      Author: gwue
 */

#include "OutputSoundComponent.h"


DEFINE_COMPONENTNAME(OutputSoundComponent, "dac");


EXPORT_SOUNDCOMPONENT_NO_IMPL(OutputSoundComponent);


OutputSoundComponent::OutputSoundComponent(std::vector<std::string> params) :
		SoundComponentImpl(params)
{
	this->buffer = NULL;

	CREATE_AND_REGISTER_PORT3(OutputSoundComponent, In, SoundPort, SoundIn, 1);
}

OutputSoundComponent::~OutputSoundComponent()
{
	this->buffer->stopThread();
	delete this->buffer;
}

void OutputSoundComponent::process()
{
    BufferedLinkPtr soundLink = boost::static_pointer_cast<BufferedLink>(m_SoundIn_1_Port->getLink());

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
