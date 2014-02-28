/*
 * GreaterCC_SW.hpp
 *
 *  Created on: Feb 24, 2014
 *      Author: posewsky
 */

#ifndef GREATERCC_SW_HPP_
#define GREATERCC_SW_HPP_

#include "../GreaterCC.hpp"

// The SW implementation class MUST have the same name as the generic base class, with the suffix _SW
class GreaterCC_SW: public GreaterCC
{

public:
	GreaterCC_SW(std::vector<std::string>);

	void process();

};

#endif
