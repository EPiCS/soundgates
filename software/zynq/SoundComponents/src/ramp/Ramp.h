/*
 * Ramp.h
 *
 *  Created on: Dec 10, 2013
 *      Author: lukas
 */

#ifndef RAMP_H_
#define RAMP_H_


#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>

class Ramp: public SoundComponentImpl {

public:
	DECLARE_COMPONENTNAME;

    /* in ports */

	DECLARE_PORT3(SoundPort, SoundIn, 1);

	DECLARE_PORT3(ControlPort, Attack, 2);
	DECLARE_PORT3(ControlPort, Release, 3);
	DECLARE_PORT3(ControlPort, Trigger, 4);

	/* in ports */
	DECLARE_PORT3(SoundPort, SoundOut, 1);

	Ramp(std::vector<std::string> params);
	virtual ~Ramp();
};


/**
 * On value changed listener
 */
class OnValueChange: public ICallbackFunctor {
private:
    float* m_pValueRef;
    ControlPortPtr m_pPort;
public:

    OnValueChange(float* ref, ControlPortPtr port) :
            m_pValueRef(ref), m_pPort(port) {
    }
    void operator()() {
        *m_pValueRef = m_pPort->pop();
    }
};


#endif /* RAMP_H_ */
