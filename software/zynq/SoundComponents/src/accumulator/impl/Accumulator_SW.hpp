/*
 * Accumulator_SW.hpp
 *
 *  Created on: Mar 24, 2014
 *      Author: posewsky
 */

#ifndef ACCUMULATOR_SW_HPP_
#define ACCUMULATOR_SW_HPP_

#include "../Accumulator.hpp"

// The SW implementation class MUST have the same name as the generic base class, with the suffix _SW
class Accumulator_SW: public Accumulator
{

public:
	Accumulator_SW(std::vector<std::string>);

	void process();

};

#endif
