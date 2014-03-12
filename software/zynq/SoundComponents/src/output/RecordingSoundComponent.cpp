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
		SoundComponentImpl(params), buffer(true)
{
	CREATE_AND_REGISTER_PORT3(RecordingSoundComponent, Out, SoundPort, SoundOut, 1);
}

RecordingSoundComponent::~RecordingSoundComponent()
{
	buffer.stopThread();
}

void RecordingSoundComponent::process()
{

	static char* bufferArray 	= m_SoundOut_1_Port->getBuffer();

	// Initializing all of the patch might take an arbitrary long time.
	// The soundbuffer thread might already be running, but we don't want to start recording unless data starts flowing
	// Therefore we call the start playback method here
	buffer.startPlayback();
	buffer.readbuffer(bufferArray, Synthesizer::config::bytesPerBlock);
}

void RecordingSoundComponent::init()
{
	std::cout << "Soundbuffer initialized" << std::endl;
}
