/*
 * MultiplyControl.h
 *
 *  Created on: Jan 17, 2014
 *      Author: lukas
 */

#ifndef MULTIPLYCONTROL_H_
#define MULTIPLYCONTROL_H_

#include <SoundComponentImpl.h>
#include <Listener.h>
#include <Synthesizer.h>
#include <SoundPort.h>
#include <ControlPort.h>
/**
 * Multiply a pcm data with a control value
 *
 */
class MultiplyControl : public SoundComponentImpl {

public:

    DECLARE_COMPONENTNAME

    DECLARE_PORT3(SoundPort, SoundIn, 1);
    DECLARE_PORT3(ControlPort, ValueIn, 2);

    DECLARE_PORT3(SoundPort, SoundOut, 1);

    float multValue;

    MultiplyControl(std::vector<std::string>);
    virtual ~MultiplyControl();
    virtual void init(void);
};

#endif /* MULTIPLYCONTROL_H_ */
