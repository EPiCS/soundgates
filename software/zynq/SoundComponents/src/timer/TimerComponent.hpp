/*
 * TemplateComponent.hpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#ifndef TIMERCOMPONENT_HPP_
#define TIMERCOMPONENT_HPP_

#include <sstream>
#include <climits>
#include <sys/time.h>
// necessary includes
#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>
#include <Listener.h>

// Implementation independant base class for some sound component
class TimerComponent: public SoundComponentImpl
{

public:

    static int instcount;

    int myid;

    float m_Enabled;
	float m_Loop;
	float m_Delay;

	bool  m_TriggeredOnce;

	struct timeval m_Timeval;
	uint64_t m_Lasttime;

	DECLARE_COMPONENTNAME
	DECLARE_PORT3(ControlPort, TriggerIn, 1);

	DECLARE_PORT3(ControlPort, Loop, 2);
	DECLARE_PORT3(ControlPort, Delay, 3);
	DECLARE_PORT3(ControlPort, TriggerOut, 1);

	// Constructor that has a parameter vector (also use this if the component has no parameters -> empty vector)
	TimerComponent(std::vector<std::string> params);
	virtual ~TimerComponent();

	// Initialization that is common regardless of SW/HW
	virtual void init(void);

	virtual void process(void) = 0;

    /**
     *  On Trigger listener
     */
    class OnTrigger : public ICallbackFunctor {
    private:
	    TimerComponent& m_ObjRef;

    public:
        OnTrigger(TimerComponent& ref) : m_ObjRef(ref) {

        }
        void operator()(){
            gettimeofday(&m_ObjRef.m_Timeval, NULL);

            m_ObjRef.m_Lasttime = m_ObjRef.m_Timeval.tv_sec * 1000 +  m_ObjRef.m_Timeval.tv_usec / 1000;

            m_ObjRef.m_TriggeredOnce = false;

            LOG_DEBUG("Timer triggered" << m_ObjRef.myid );
        }
    };

};

#endif /* TIMERCOMPONENT_HPP_ */
