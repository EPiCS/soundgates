/*
 * TemplateSoundComponent_SW.cpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#include "TimerComponent_SW.hpp"

TimerComponent_SW::TimerComponent_SW(std::vector<std::string> params) : TimerComponent(params)
{
	// implementation specific initialization stuff here
}

// The actual software processing
// In this example, we take an incoming control value and write it to each sample on the sound output
// and take an incoming sound signal, average over the samples and write it to the control output
void TimerComponent_SW::process()
{
	struct timeval tv;
	gettimeofday(&tv, NULL);
	unsigned long long now = tv.tv_sec * 1000 + tv.tv_usec / 1000;
	// Access the control value. We implemented a callback method in the superclass which handles control values.
	if(now-lastTime >= (unsigned long long)duration) {
		out = !out;
		lastTime = now;
	}
	std::cout << now << " " << lastTime << " " << duration << " " << now-lastTime << std::endl;
	char bla;
	//std::cin >> bla;

	// finally, push a value to the outgoing control port
	m_TimerOut_1_Port->push(out ? 1.0f : 0.0f);
}
