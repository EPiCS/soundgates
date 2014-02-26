/*
 * Demux_SW.cpp
 *
 *  Created on: Feb 22, 2014
 *      Author: posewsky
 */

#include "Demux_SW.hpp"

Demux_SW::Demux_SW(std::vector<std::string> params) : Demux(params)
{
	// implementation specific initialization stuff here
}

// The actual software processing
// In this example, we take an incoming control value and write it to each sample on the sound output
// and take an incoming sound signal, average over the samples and write it to the control output
void Demux_SW::process()
{
	if(!dirty)
		return;

	if(sel) {
		m_DemuxOut2_2_Port->push(in);
	} else {
		m_DemuxOut1_1_Port->push(in);
	}
	dirty = false;
}
