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
#include <iconv.h>
#include "../SineSoundComponent.h"


class SineSoundComponent_SW : public SineSoundComponent {
private:

	float m_Phase;

public:

	SineSoundComponent_SW(std::vector<std::string>);

	void process();

};

#endif /* SINEIMPL_HW_H_ */
