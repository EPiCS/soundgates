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
		SoundComponentImpl(params) , m_Buffer(false)
{
	CREATE_AND_REGISTER_PORT3(OutputSoundComponent, In, SoundPort, SoundIn, 1);
}

OutputSoundComponent::~OutputSoundComponent()
{
	m_Buffer.stopThread();
}

void OutputSoundComponent::process()
{
	static char* bufferArray = m_SoundIn_1_Port->getBuffer();

	// Initializing all of the patch might take an arbitrary long time.
	// The soundbuffer thread might already be running, but we don't want to start playback unless data starts flowing
	// Therefore we call the start playback method here
	m_Buffer.startPlayback();
	m_Buffer.fillbuffer(bufferArray, Synthesizer::config::bytesPerBlock);

}

void OutputSoundComponent::init()
{
    // empty
}
