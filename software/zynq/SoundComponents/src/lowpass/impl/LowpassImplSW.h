/*
 * FilterImplSw.h
 *
 *  Created on: Dec 7, 2013
 *      Author: lukas
 */

#ifndef LOWPASSIMPLSW_H_
#define LOWPASSIMPLSW_H_

#include "../LowpassComponent.h"


static float coeffs[Synthesizer::config::blocksize / 2] = {
		  -537211474580.0054,
		  -352341521829.2335,
		  -259359432183.73788,
		  92207980517.70227,
		  209317623596.58673,
		  163381551240.74503,
		  327234139771.05524,
		  185780207547.6387,
		  242074258033.60602,
		  480655402862.63965,
		  317674308878.1012,
		  348910585229.01874,
		  266894046030.31686,
		  -135530562031.94753,
		  -426490161960.42957,
		  -923196951121.6516,
		  -923196951121.6516,
		  -426490161960.42957,
		  -135530562031.94753,
		  266894046030.31686,
		  348910585229.01874,
		  317674308878.1012,
		  480655402862.63965,
		  242074258033.60602,
		  185780207547.6387,
		  327234139771.05524,
		  163381551240.74503,
		  209317623596.58673,
		  92207980517.70227,
		  -259359432183.73788,
		  -352341521829.2335,
		  -537211474580.0054
};

class LowpassImplSW: public LowpassComponent {

private:
	int 	m_BufferSize;
	char* 	m_Buffer;

public:
	LowpassImplSW(std::vector<std::string>);
	virtual ~LowpassImplSW();

	void init(void);
	void process(void);

};

#endif /* LOWPASSIMPLSW_H_ */
