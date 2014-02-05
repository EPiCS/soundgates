/*
 * TemplateSoundComponent_SW.hpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#ifndef FIR_SW_HPP_
#define FIR_SW_HPP_

#include "../FIR.hpp"
#include <Listener.h>
// The SW implementation class MUST have the same name as the generic base class, with the suffix _SW
class FIR_SW: public FIR
{

public:

    int m_LocalBuffer[N_FIR_COEFF -1 + Synthesizer::config::blocksize];

	FIR_SW(std::vector<std::string>);

	void init();
	void process();

};

#endif /* FIR_SW_HPP_ */
