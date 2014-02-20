/*
 * LoggingComponent_SW.hpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#ifndef _LOGGINGCOMPONENT_SW_HPP_
#define _LOGGINGCOMPONENT_SW_HPP_

#include "../LoggingComponent.hpp"

class LoggingComponent_SW: public LoggingComponent
{

public:
	LoggingComponent_SW(std::vector<std::string>);

	void process();

};

#endif /* LoggingComponent_SW_HPP_ */
