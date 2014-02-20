/*
 * DivSCComponent.h
 *
 *  Created on: Feb 8, 2014
 *      Author: gwue
 */

#ifndef DivSCComponent_H_
#define DivSCComponent_H_

#include <SoundComponentImpl.h>
#include <Listener.h>
#include <Synthesizer.h>
#include <SoundPort.h>
#include <ControlPort.h>
/**
 * Divide a pcm data by a control value
 *
 */
class DivSCComponent : public SoundComponentImpl {

public:

    DECLARE_COMPONENTNAME

    DECLARE_PORT3(SoundPort, SoundIn, 1);
    DECLARE_PORT3(ControlPort, ValueIn, 2);

    DECLARE_PORT3(SoundPort, SoundOut, 1);

    float divValue;

    DivSCComponent(std::vector<std::string>);
    virtual ~DivSCComponent();
    virtual void init(void);
};

#endif /* MULTIPLYCONTROL_H_ */
