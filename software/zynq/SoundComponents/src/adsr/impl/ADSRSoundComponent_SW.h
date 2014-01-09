/*
 * ADSRImplSW.h
 *
 *  Created on: Dec 10, 2013
 *      Author: lukas
 */

#ifndef ADSRIMPLSW_H_
#define ADSRIMPLSW_H_

#include "../ADSRSoundComponent.h"

#define ADSR_IS_RETRIGGERED(a, b) a == 0 && b > 0

class ADSRSoundComponent_SW : public ADSRSoundComponent{

private:

    enum eADSRStateType {   IDLE, ATTACK, DECAY, SUSTAIN, RELEASE   };

    enum eADSRStateType m_ADSRState;

    int                 m_SamplesProcessed;

    float               m_LastTriggerState;

    int                 m_attacksamplecount;
    int                 m_decaysamplecount;
    int                 m_releasesamplecount;

    float               m_sustainlevel;
    float               m_currentlevel;

public:
    ADSRSoundComponent_SW(std::vector<std::string> params);
	virtual ~ADSRSoundComponent_SW();

	void init();
	void process();

	void handleRetrigger(int&, SoundPort&, SoundPort&);

};

#endif /* ADSRIMPLSW_H_ */
