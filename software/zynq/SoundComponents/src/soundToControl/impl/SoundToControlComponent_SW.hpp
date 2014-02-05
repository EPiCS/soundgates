/*
 * SoundToControlComponent_SW.hpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#ifndef STOCCOMPONENT_SW_HPP_
#define STOCCOMPONENT_SW_HPP_

#include "../SoundToControlComponent.hpp"

class SoundToControlComponent_SW: public SoundToControlComponent
{

public:
	SoundToControlComponent_SW(std::vector<std::string>);

	void process();

};

#endif
