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


class NoiseImplSW : public NoiseSoundComponent {
public:
	NoiseImplSW(std::vector<std::string>);
	virtual ~NoiseImplSW();

	void init();
	void process();
};

#endif /* NOISEIMPLSW_H_ */
