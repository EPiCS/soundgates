/*
 * Or_SW.hpp
 *
 *  Created on: Feb 23, 2014
 *      Author: posewsky
 */

#ifndef OR_SW_HPP_
#define OR_SW_HPP_

#include "../Or.hpp"

// The SW implementation class MUST have the same name as the generic base class, with the suffix _SW
class Or_SW: public Or
{

public:
	Or_SW(std::vector<std::string>);

	void process();

};

#endif
