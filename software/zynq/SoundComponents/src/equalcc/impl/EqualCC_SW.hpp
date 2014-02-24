/*
 * EqualCC_SW.hpp
 *
 *  Created on: Feb 24, 2014
 *      Author: posewsky
 */

#ifndef EqualCC_SW_HPP_
#define EqualCC_SW_HPP_

#include "../EqualCC.hpp"

// The SW implementation class MUST have the same name as the generic base class, with the suffix _SW
class EqualCC_SW: public EqualCC
{

public:
	EqualCC_SW(std::vector<std::string>);

	void process();

};

#endif
