/*
 * PulseOutputComponent.h
 *
 *  Created on: Nov 30, 2013
 *      Author: lfu
 *
 * TODO: add useful description here
 */

#ifndef OUTPUTSOUNDCOMPONENT_H_
#define OUTPUTSOUNDCOMPONENT_H_

#include <string>
#include <vector>

#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>

#include <pulse/simple.h>
#include <pulse/error.h>

#define C_PULSEAUDIO_CLIENT_NAME "Soundgates Synthesizer"

class PulseaudioOutputComponent: public SoundComponentImpl
{
    private:
        const pa_sample_spec m_PASampleSpec;

        pa_simple *    m_PAConnection;
        int            m_PAErrorCode;

    public:

        DECLARE_COMPONENTNAME

        DECLARE_PORT3(SoundPort, SoundIn, 1);

        PulseaudioOutputComponent(std::vector<std::string> params);
        virtual ~PulseaudioOutputComponent();

        void init(void);
        void process(void);

};

#endif /* OUTPUTSOUNDCOMPONENT_H_ */
