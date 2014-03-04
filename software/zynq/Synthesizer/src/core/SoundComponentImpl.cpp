/*
 * SoundComponentImpl.cpp
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */

#include "SoundComponentImpl.h"

SoundComponentImpl::SoundComponentImpl() {
	implType = 0;
}

SoundComponentImpl::SoundComponentImpl(std::vector<std::string> parameters){
	implType = 0;
	this->m_Parameters = parameters;
}

SoundComponentImpl::~SoundComponentImpl(){ }


PortPtr SoundComponentImpl::getInport(unsigned int number){

	if(m_Inports.size() < number){
		std::stringstream s;
		s << "In Port number out of range: " << number;
		throw std::out_of_range(s.str().c_str());
	}

	return m_Inports[(number - 1)];
}

PortPtr SoundComponentImpl::getOutport(unsigned int number){

	if(m_Outports.size() < number){
		std::stringstream s;
		s << "Out Port number out of range: " << number;
	    throw std::out_of_range(s.str().c_str());
	}

	return m_Outports[(number - 1)];
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
