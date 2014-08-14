/*
 * SoundComponentImpl.cpp
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */

#include "SoundComponentImpl.h"

SoundComponentImpl::SoundComponentImpl(){

    m_Outports.reserve(Synthesizer::config::nPortsMax);
    m_Inports.reserve(Synthesizer::config::nPortsMax);

	implType = 0;
}

SoundComponentImpl::SoundComponentImpl(std::vector<std::string> parameters){
	implType = 0;
	m_Parameters = parameters;
}

SoundComponentImpl::~SoundComponentImpl(){ }


PortPtr SoundComponentImpl::getInport(std::size_t nIndex){

	if(m_Inports.size() < nIndex){
		std::stringstream s;
		s << "In Port number out of range: " << nIndex;
		throw std::out_of_range(s.str());
	}

	return m_Inports[(nIndex - 1)];
}

PortPtr SoundComponentImpl::getOutport(std::size_t nIndex){

	if(m_Outports.size() < nIndex){
		std::stringstream s;
		s << "Out Port number out of range: " << nIndex;
	    throw std::out_of_range(s.str());
	}

	return m_Outports[(nIndex - 1)];
}


std::vector<PortPtr>& SoundComponentImpl::getInports(){

    return m_Inports;
}

std::vector<PortPtr>& SoundComponentImpl::getOutports(){

    return m_Outports;
}

void SoundComponentImpl::initLater(){
}

void SoundComponentImpl::setImplType(const char* t) {
	this->implType = t;
}
const char* SoundComponentImpl::getImplType() {
	return this->implType;
}
