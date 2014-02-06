/*
 * DebuggingSoundComponent_SW.hpp
 *
 *  Created on: Jan 23, 2014
 *  @author: CaiusC
 */

#ifndef DebuggingSoundComponent_SW_HPP_
#define DebuggingSoundComponent_SW_HPP_

#include "../DebuggingSoundComponent.hpp"
#include <climits>

// The SW implementation class MUST have the same name as the generic base class, with the suffix _SW
class DebuggingSoundComponent_SW: public DebuggingSoundComponent
{

public:
	DebuggingSoundComponent_SW(std::vector<std::string>);

	void process();

};

#endif /* DebuggingSoundComponent_SW_HPP_ */
