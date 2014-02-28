/*
 * LessCC_SW.hpp
 *
 *  Created on: Feb 24, 2014
 *      Author: posewsky
 */

#ifndef LessCC_SW_HPP_
#define LessCC_SW_HPP_

#include "../LessCC.hpp"

// The SW implementation class MUST have the same name as the generic base class, with the suffix _SW
class LessCC_SW: public LessCC
{

public:
	LessCC_SW(std::vector<std::string>);

	void process();

};

#endif
