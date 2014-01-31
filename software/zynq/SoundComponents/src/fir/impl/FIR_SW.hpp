/*
 * TemplateSoundComponent_SW.hpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#ifndef FIR_SW_HPP_
#define FIR_SW_HPP_

#include "../FIR.hpp"

// The SW implementation class MUST have the same name as the generic base class, with the suffix _SW
class FIR_SW: public FIR
{

public:
	FIR_SW(std::vector<std::string>);

	void process();

};

#endif /* FIR_SW_HPP_ */
