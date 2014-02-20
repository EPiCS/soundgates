/*
 * SquareImpl_HW.h
 *
 *  Created on: Nov 29, 2013
 *      Author: gwue
 */

#ifndef SquareIMPL_SW_H_
#define SquareIMPL_SW_H_

#include <cmath>
#include <iostream>
#include <climits>

#include "../SquareSoundComponent.h"

class SquareSoundComponent_SW : public SquareSoundComponent {

private:

	double phase;

public:

	SquareSoundComponent_SW(std::vector<std::string>);
	~SquareSoundComponent_SW();

	void init();
	void process();

};


#endif /* SquareIMPL_HW_H_ */
