/*
 * MultiplyControlSW.h
 *
 *  Created on: Jan 17, 2014
 *      Author: lukas
 */

#ifndef MULTIPLYCONTROLSW_H_
#define MULTIPLYCONTROLSW_H_

#include "../MultiplyControl.h"

class MultiplyControl_SW : public MultiplyControl{
public:
    MultiplyControl_SW(std::vector<std::string>);
    virtual ~MultiplyControl_SW();

    void init();

    void process();

};

#endif /* MULTIPLYCONTROLSW_H_ */
