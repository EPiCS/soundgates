/*
 * Demux_SW.hpp
 *
 *  Created on: Feb 22, 2014
 *      Author: posewsky
 */

#ifndef DEMUX_SW_HPP_
#define DEMUX_SW_HPP_

#include "../Demux.hpp"

// The SW implementation class MUST have the same name as the generic base class, with the suffix _SW
class Demux_SW: public Demux
{

public:
	Demux_SW(std::vector<std::string>);

	void process();

};

#endif
