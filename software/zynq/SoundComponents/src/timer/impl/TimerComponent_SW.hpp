/*
 * TemplateSoundComponent_SW.hpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#ifndef TIMERCOMPONENT_SW_HPP_
#define TIMERCOMPONENT_SW_HPP_

#include "../TimerComponent.hpp"

// The SW implementation class MUST have the same name as the generic base class, with the suffix _SW
class TimerComponent_SW: public TimerComponent
{

public:
	TimerComponent_SW(std::vector<std::string>);

	void process();

};

#endif /* TIMERCOMPONENT_SW_HPP_ */
