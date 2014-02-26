/*
 * Mux_SW.hpp
 *
 *  Created on: Feb 21, 2014
 *      Author: posewsky
 */

#ifndef MUX_SW_HPP_
#define MUX_SW_HPP_

#include "../Mux.hpp"

// The SW implementation class MUST have the same name as the generic base class, with the suffix _SW
class Mux_SW: public Mux
{

public:
	Mux_SW(std::vector<std::string>);

	void process();

};

#endif
