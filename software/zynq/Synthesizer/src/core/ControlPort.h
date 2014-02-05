/*
 * ControlPort.h
 *
 *  Created on: Dec 5, 2013
 *      Author: lukas
 */

#ifndef CONTROLPORT_H_
#define CONTROLPORT_H_

#include <vector>
#include <boost/smart_ptr.hpp>
#include "Port.h"
#include "Synthesizer.h"
#include "ICallbackFunctor.h"

class ControlPort;

typedef boost::shared_ptr<ControlPort>  ControlPortPtr;

class ControlPort : public Port{

private:

    std::vector<ICallbackPtr>* m_Callbacks;
public:

	ControlPort(int portnumber);
	virtual ~ControlPort();

	/*
	 * Initialize control port
	 *
	 */
	int init();

	/*
	 * Called when connected link has new data
	 */
    void notify();

	/**
	 * Returns last valid control value
	 *
	 * @return
	 */
	float pop();

	/**
	 * Store control value
	 *
	 * @param value
	 */
	void push(float value);


	/**
	 * Register a callback functor
	 *
	 * @param functor
	 */
	void registerCallback(const ICallbackPtr&);

};

#endif /* CONTROLPORT_H_ */
