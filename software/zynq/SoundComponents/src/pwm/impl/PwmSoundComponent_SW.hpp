/*
 * PwmSoundComponent_SW.hpp
 *
 *  Created on: Jan 23, 2014
 */

#ifndef PWMSOUNDCOMPONENT_SW_HPP_
#define PWMSOUNDCOMPONENT_SW_HPP_

#include "../PwmSoundComponent.hpp"
#include <climits>

// The SW implementation class MUST have the same name as the generic base class, with the suffix _SW
class PwmSoundComponent_SW: public PwmSoundComponent
{

public:
	PwmSoundComponent_SW(std::vector<std::string>);

	void process();

};

#endif /* TEMPLATESOUNDCOMPONENT_SW_HPP_ */
