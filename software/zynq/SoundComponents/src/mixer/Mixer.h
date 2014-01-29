/*
 * Mixer.h
 *
 *  Created on: Jan 2, 2014
 *      Author: lukas
 */

#ifndef MIXER_H_
#define MIXER_H_

#include <sstream>
#include <limits.h>

#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>

class Mixer: SoundComponentImpl
{

public:

	DECLARE_COMPONENTNAME
	;

	DECLARE_PORT3(SoundPort, SoundIn, 1);
	DECLARE_PORT3(SoundPort, SoundIn, 2);

	DECLARE_PORT3(ControlPort, BiasIn, 3);

	DECLARE_PORT3(SoundPort, SoundOut, 1);

	float m_bias;

	Mixer(std::vector<std::string> params);
	virtual ~Mixer();
};

class OnBiasChange: public ICallbackFunctor
{
private:
	Mixer& m_ObjRef;
public:
	OnBiasChange(Mixer& ref) :
			m_ObjRef(ref)
	{
	}

	void operator()()
	{
		float val = m_ObjRef.m_BiasIn_3_Port->pop();
		if (val < 0)
		{
			m_ObjRef.m_bias = 0.0f;
		}
		else if (val > 1)
		{
			m_ObjRef.m_bias = 1.0f;
		}
		else
		{
			m_ObjRef.m_bias = val;
		}
	}
};

#endif /* MIXER_H_ */
