/*
 * TriangleSoundComponent_SW.h
 */

#ifndef TriangleSoundComponent_SW_SW_H_
#define TriangleSoundComponent_SWL_SW_H_

#include <cmath>
#include <iostream>
#include <climits>
#include <iconv.h>
#include "../TriangleSoundComponent.h"

class TriangleSoundComponent_SW : public TriangleSoundComponent {
private:

	float m_Phase;

public:

	TriangleSoundComponent_SW(std::vector<std::string>);

	void process();

};

#endif
