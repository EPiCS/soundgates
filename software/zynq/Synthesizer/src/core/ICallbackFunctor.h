/*
 * ICallbackFunctor.h
 *
 *  Created on: Jan 10, 2014
 *      Author: lukas
 */

#ifndef ICALLBACKFUNCTOR_H_
#define ICALLBACKFUNCTOR_H_

#include <boost/smart_ptr.hpp>

#include "SoundComponentImpl.h"


class ICallbackFunctor {

public:

    ICallbackFunctor();
    virtual ~ICallbackFunctor();

    virtual void operator()(void) = 0;

};

typedef boost::shared_ptr<ICallbackFunctor>  ICallbackPtr;

#endif /* ICALLBACKFUNCTOR_H_ */
