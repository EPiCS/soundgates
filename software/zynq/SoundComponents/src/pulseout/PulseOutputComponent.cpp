/*
 * PulseOutputComponent.cpp
 *
 *  Created on: Nov 30, 2013
 *      Author: lfu
 */

#include "PulseOutputComponent.h"


DEFINE_COMPONENTNAME(PulseaudioOutputComponent, "pulseout");

EXPORT_SOUNDCOMPONENT_NO_IMPL(PulseaudioOutputComponent);


static pa_sample_spec createSampleSpec() {pa_sample_spec spec = {
                                                        PA_SAMPLE_S32LE,
                                                        Synthesizer::config::samplerate,
                                                        1};
                                                    return spec;
                                   }

PulseaudioOutputComponent::PulseaudioOutputComponent(std::vector<std::string> params)
            : SoundComponentImpl(params),
              m_PASampleSpec(createSampleSpec())
{
	CREATE_AND_REGISTER_PORT3(PulseaudioOutputComponent, In, SoundPort, SoundIn, 1);
	m_PAErrorCode  = 0x00;
	m_PAConnection = NULL;
}

PulseaudioOutputComponent::~PulseaudioOutputComponent()
{
    pa_simple_free(m_PAConnection);
}

void PulseaudioOutputComponent::process()
{
	static char* bufferArray = m_SoundIn_1_Port->getBuffer();

    /* Make sure that every single sample was played */
//    if (pa_simple_drain(m_PAConnection, &m_PAErrorCode) < 0)
//    {
//        LOG_ERROR("Error while waiting for audio stream! Code: " << m_PAErrorCode);
//    }

	 /* ... and play it */
	if (pa_simple_write(m_PAConnection, bufferArray, (size_t) Synthesizer::config::bytesPerBlock, &m_PAErrorCode) < 0)
	{
	    LOG_ERROR("Error while writing to audio stream! Code: " << m_PAErrorCode);
	}
}

void PulseaudioOutputComponent::init()
{
    /* Create a new playback stream */
   if (!(m_PAConnection = pa_simple_new(NULL, C_PULSEAUDIO_CLIENT_NAME, PA_STREAM_PLAYBACK, NULL, "playback", &m_PASampleSpec, NULL, NULL, &m_PAErrorCode)))
   {
       LOG_ERROR("Could not create pulse audio output stream! Error: " << m_PAErrorCode);
   }

   pa_usec_t latency;

   if ((latency = pa_simple_get_latency(m_PAConnection, &m_PAErrorCode)) == (pa_usec_t) -1)
   {
       LOG_ERROR("Could not create pulse audio latency! Error: " << m_PAErrorCode);
   }

   LOG_INFO("Pulseaudio stream initialized. Running with latency: " << (float)latency << "us");
}

