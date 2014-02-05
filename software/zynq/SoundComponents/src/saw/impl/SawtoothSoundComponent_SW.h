/*
 * SawtoothImpl_HW.h
 *
 *  Created on: Nov 29, 2013
 *      Author: gwue
 */

#ifndef SawtoothIMPL_SW_H_
#define SawtoothIMPL_SW_H_

#include <cmath>
#include <iostream>
#include <climits>

#include "../SawtoothSoundComponent.h"

class SawtoothSoundComponent_SW : public SawtoothSoundComponent {

private:

	double phase;

public:

	SawtoothSoundComponent_SW(std::vector<std::string>);
	~SawtoothSoundComponent_SW();

	void init();
	void process();

};


#endif /* SawtoothIMPL_HW_H_ */
