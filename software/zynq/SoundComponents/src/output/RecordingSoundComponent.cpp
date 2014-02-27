/*
 * RecordingSoundComponent.cpp
 *
 *  Created on: Feb 27, 2014
 *      Author: gwue
 */

#include "RecordingSoundComponent.h"


DEFINE_COMPONENTNAME(RecordingSoundComponent, "adc");


EXPORT_SOUNDCOMPONENT_NO_IMPL(RecordingSoundComponent);


RecordingSoundComponent::RecordingSoundComponent(std::vector<std::string> params) :
		SoundComponentImpl(params)
{
	this->buffer = NULL;

	CREATE_AND_REGISTER_PORT3(RecordingSoundComponent, Out, SoundPort, SoundOut, 1);
}

RecordingSoundComponent::~RecordingSoundComponent()
{
	this->buffer->stopThread();
	delete this->buffer;
}

void RecordingSoundComponent::process()
{


    BufferedLinkPtr soundLink = boost::static_pointer_cast<BufferedLink>(m_SoundOut_1_Port->getLink());

	char* bufferArray 	= soundLink->getReadBuffer();
	int bufferSize 		= soundLink->getBufferDepth();

	// Initializing all of the patch might take an arbitrary long time.
	// The soundbuffer thread might already be running, but we don't want to start recording unless data starts flowing
	// Therefore we call the start playback method here
	this->buffer->startPlayback();
	this->buffer->readbuffer(bufferArray,bufferSize);

	std::stringstream ss;
	for (int i =0; i < bufferSize; i++ ) {
		ss << bufferArray[i];
	}
}

void RecordingSoundComponent::init()
{
	this->buffer = new Soundbuffer(true);

	std::cout << "Soundbuffer initialized" << std::endl;
}
