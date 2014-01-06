/*
 * ADSRImplSW.h
 *
 *  Created on: Dec 10, 2013
 *      Author: lukas
 */

#ifndef ADSRIMPLSW_H_
#define ADSRIMPLSW_H_

#include "../ADSRSoundComponent.h"

class ADSRSoundComponent_SW : public ADSRSoundComponent{

private:

    enum eADSRStateType {   IDLE, ATTACK, DECAY, SUSTAIN, RELEASE   };

    enum eADSRStateType m_ADSRState;

    int                 m_SamplesProcessed;

public:
    ADSRSoundComponent_SW(std::vector<std::string> params);
	virtual ~ADSRSoundComponent_SW();

	void init();
	void process();
};

#endif /* ADSRIMPLSW_H_ */
