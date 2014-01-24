/*
 * TemplateSoundComponent_SW.hpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#ifndef TEMPLATESOUNDCOMPONENT_SW_HPP_
#define TEMPLATESOUNDCOMPONENT_SW_HPP_

#include "../TemplateSoundComponent.hpp"

// The SW implementation class MUST have the same name as the generic base class, with the suffix _SW
class TemplateSoundComponent_SW: public TemplateSoundComponent
{

public:
	TemplateSoundComponent_SW(std::vector<std::string>);

	void process();

};

#endif /* TEMPLATESOUNDCOMPONENT_SW_HPP_ */
