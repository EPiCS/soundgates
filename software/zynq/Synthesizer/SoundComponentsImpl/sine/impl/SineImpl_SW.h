/*
 * SineImpl_HW.h
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */

#ifndef SINEIMPL_SW_H_
#define SINEIMPL_SW_H_

#include <cmath>
#include <iostream>
#include <climits>

#include "../SineSoundComponent.h"

class SineImpl_SW : public SineSoundComponent {

private:

	double phase;

public:

	SineImpl_SW(std::vector<std::string>);

	void init();
	void process();

};


#endif /* SINEIMPL_HW_H_ */
