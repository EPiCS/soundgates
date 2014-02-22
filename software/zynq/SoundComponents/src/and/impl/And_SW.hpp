/*
 * And_SW.hpp
 *
 *  Created on: Feb 22, 2014
 *      Author: posewsky
 */

#ifndef AND_SW_HPP_
#define AND_SW_HPP_

#include "../And.hpp"

// The SW implementation class MUST have the same name as the generic base class, with the suffix _SW
class And_SW: public And
{

public:
	And_SW(std::vector<std::string>);

	void process();

};

#endif
