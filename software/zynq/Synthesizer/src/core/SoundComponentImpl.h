/*
 * SoundComponentImpl.h
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */

#ifndef SOUNDCOMPONENTIMPL_H_
#define SOUNDCOMPONENTIMPL_H_

#include <vector>
#include <string.h>
#include <iostream>

#include "SoundPort.h"
#include "ControlPort.h"
#include "BufferedLink.h"
#include "ControlLink.h"
#include "Port.h"

    #define EXPORT_SOUNDCOMPONENT_MIXED_IMPL(CLASSNAME)                             \
            extern "C" {                                                            \
                CLASSNAME * create(SoundComponents::ImplType impltype,              \
                    std::vector<std::string> params) {                              \
                    if(impltype == SoundComponents::HW){                            \
                        return new  CLASSNAME ## _HW(params);                       \
                    }else if(impltype == SoundComponents::SW){                      \
                        return new  CLASSNAME ## _SW(params);                       \
                    }                                                               \
                    return NULL; }                                                  \
                void destroy(CLASSNAME * cmp) { delete cmp;   }                     \
                const char* getComponentName() { return CLASSNAME::name; } }

    #define EXPORT_SOUNDCOMPONENT_SW_ONLY(CLASSNAME)                                \
            extern "C" {                                                            \
                CLASSNAME * create(SoundComponents::ImplType impltype,              \
                    std::vector<std::string> params) {                              \
                    if(impltype == SoundComponents::HW){                            \
                        return NULL;                                                \
                    }else if(impltype == SoundComponents::SW){                      \
                        return new  CLASSNAME ## _SW(params);                       \
                    }                                                               \
                    return NULL; }                                                  \
                void destroy(CLASSNAME * cmp) { delete cmp;   }                     \
                const char* getComponentName() { return CLASSNAME::name; } }

    #define EXPORT_SOUNDCOMPONENT_HW_ONLY(CLASSNAME)                                \
            extern "C" {                                                            \
                CLASSNAME * create(SoundComponents::ImplType impltype,              \
                    std::vector<std::string> params) {                              \
                    if(impltype == SoundComponents::HW){                            \
                        return new  CLASSNAME ## _HW(params);                       \
                    }else if(impltype == SoundComponents::SW){                      \
                        return NULL;                                                \
                    }                                                               \
                    return NULL; }                                                  \
                void destroy(CLASSNAME * cmp) { delete cmp;   }                     \
                const char* getComponentName() { return CLASSNAME::name; } }

    #define EXPORT_SOUNDCOMPONENT_NO_IMPL(CLASSNAME)                            \
            extern "C" {                                                        \
                CLASSNAME * create(SoundComponents::ImplType impltype,          \
                    std::vector<std::string> params) {                          \
                        return new  CLASSNAME (params); }                       \
                void destroy(CLASSNAME * cmp) { delete cmp; }                   \
                const char* getComponentName() { return CLASSNAME::name; } }

#define DECLARE_COMPONENTNAME   \
        static const char* name;

#define DEFINE_COMPONENTNAME(CLASSNAME, VALUE)  \
        const char* CLASSNAME::name = VALUE;

#define DECLARE_PORT(TYPE, PORTNAME)  \
        TYPE*   m_ ## PORTNAME ## Port;           \
        static int s_ ## PORTNAME ## _PortNumber;

#define DECLARE_PORT2(TYPE, PORTNAME, PORTNUMBER) \
		TYPE*	m_ ## PORTNAME ## _ ## PORTNUMBER ## _ ## Port; \
		const static int s_ ## PORTNAME ## _ ## PORTNUMBER = PORTNUMBER;

#define DEFINE_PORT(CLASSNAME, PORTNAME, NUMBER) \
        int CLASSNAME::s_ ## PORTNAME ## _PortNumber = NUMBER;

#define CREATE_AND_REGISTER_PORT(CLASSNAME, DIRECTION, TYPE, PORTNAME)                  \
        m_ ## PORTNAME ## Port  = new TYPE(CLASSNAME::s_ ## PORTNAME ## _PortNumber);   \
        get## DIRECTION ##ports().push_back(m_ ## PORTNAME ## Port);

#define CREATE_AND_REGISTER_PORT2(CLASSNAME, DIRECTION, TYPE, PORTNAME, PORTNUMBER) \
        m_ ## PORTNAME ## _ ## PORTNUMBER ## _ ## Port =                            \
        new TYPE(CLASSNAME::s_ ## PORTNAME ## _ ## PORTNUMBER);                     \
        get## DIRECTION ##ports().push_back(m_ ## PORTNAME ## _ ## PORTNUMBER ## _ ## Port);

class SoundComponentImpl {

private:
	std::vector<Port*> m_Inports;
	std::vector<Port*> m_Outports;

	std::vector<std::string> m_Parameters;

public:

	SoundComponentImpl();
	SoundComponentImpl(std::vector<std::string> parameters);

	virtual ~SoundComponentImpl();

	Port* getInport(unsigned int);
	Port* getOutport(unsigned int);

	std::vector<Port*>& getInports(){ return m_Inports; }
	std::vector<Port*>& getOutports(){ return m_Outports;}
	std::vector<std::string>& getParameters(){ return m_Parameters;}

	virtual void init() = 0;
	virtual void process() = 0;

};

template<typename T> SoundComponentImpl * createInstance(std::vector<std::string> params) { return new T(params); }

#endif /* SOUNDCOMPONENTIMPL_H_ */
