/*
 * GreaterCC_SW.cpp
 *
 *  Created on: Feb 24, 2014
 *      Author: posewsky
 */

#include "GreaterCC_SW.hpp"

GreaterCC_SW::GreaterCC_SW(std::vector<std::string> params) : GreaterCC(params)
{
	// implementation specific initialization stuff here
}

// The actual software processing
// In this example, we take an incoming control value and write it to each sample on the sound output
// and take an incoming sound signal, average over the samples and write it to the control output
void GreaterCC_SW::process()
{
	if(!dirty)
		return;

	m_Out_1_Port->push((in0 > in1) ? 1.0f : 0.0f);
	dirty = false;
}
