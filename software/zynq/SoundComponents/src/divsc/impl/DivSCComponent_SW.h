/*
 * DivSCComponent_SW.h
 *
 *  Created on: Feb 8, 2014
 *      Author: gwue
 */

#ifndef DivSCComponent_SW_H_
#define DivSCComponent_SW_H_

#include "../DivSCComponent.h"

class DivSCComponent_SW : public DivSCComponent{
public:
	DivSCComponent_SW(std::vector<std::string>);
    virtual ~DivSCComponent_SW();

    void process();

};

#endif /* MULTIPLYCONTROLSW_H_ */
