/*
 * NoiseImplSW.h
 *
 *  Created on: Dec 7, 2013
 *      Author: lukas
 */

#ifndef NOISEIMPLSW_H_
#define NOISEIMPLSW_H_

#include <cstdlib>

#include "../NoiseSoundComponent.h"


class NoiseSoundComponent_SW : public NoiseSoundComponent {
public:
	NoiseSoundComponent_SW(std::vector<std::string>);
	virtual ~NoiseSoundComponent_SW();

	void init();
	void process();
};

#endif /* NOISEIMPLSW_H_ */
