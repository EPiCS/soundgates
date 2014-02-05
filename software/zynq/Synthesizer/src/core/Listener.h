/*
 * Listener.h
 *
 *  Created on: Jan 17, 2014
 *      Author: lukas
 */

#ifndef LISTENER_H_
#define LISTENER_H_

/**
 * On value changed listener
 */

#include "ControlPort.h"

template<typename C, typename P = ControlPortPtr, typename V = float>
class OnValueChange: public ICallbackFunctor {
private:
    V&  m_pValueRef;
    const P&  m_pPort;
public:

    OnValueChange(V& ref, const P& port) :
            m_pValueRef(ref), m_pPort(port) {
    }
    void operator()() {
        m_pValueRef = m_pPort->pop();
    }
};

#endif /* LISTENER_H_ */
