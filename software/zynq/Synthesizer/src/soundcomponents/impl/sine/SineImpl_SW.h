/*
 * SineImpl_HW.h
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */

#ifndef SINEIMPL_HW_H_
#define SINEIMPL_HW_H_

#include <cmath>
#include <iostream>
#include <climits>
#include "../../SineSoundComponent.h"

class SineImpl_SW : public SineSoundComponent{

private:

	float phase;

public:
	void init();
	void process();

};


#endif /* SINEIMPL_HW_H_ */
