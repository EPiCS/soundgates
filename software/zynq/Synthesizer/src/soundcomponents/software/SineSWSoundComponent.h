/*
 * SineSWSoundComponent.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef SINESWSOUNDCOMPONENT_H_
#define SINESWSOUNDCOMPONENT_H_

#include <cmath>


#include "../../Node.h"
#include "../../Port.h"
#include "../../SWSoundComponent.h"
#include "../../BufferedLink.h"

class SineSWSoundComponent : public SWSoundComponent {

	static int sineValueOutPort;
	static int frequencyInPort;

private:
	float phase;

public:

	SineSWSoundComponent();
	~SineSWSoundComponent();

	void init(void);
	void process(void);


	inline float getPhaseIncrement(float frequency);

};


#endif /* SINESWSOUNDCOMPONENT_H_ */
