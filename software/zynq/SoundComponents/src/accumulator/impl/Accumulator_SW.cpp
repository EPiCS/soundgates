/*
 * Accumulator_SW.cpp
 *
 *  Created on: Mar 24, 2014
 *      Author: posewsky
 */

#include "Accumulator_SW.hpp"

Accumulator_SW::Accumulator_SW(std::vector<std::string> params) : Accumulator(params){

}

void Accumulator_SW::process(){
	gettimeofday(&t2, NULL);
	elapsedTime = (t2.tv_sec - t1.tv_sec) * 1000.0;  // s to ms
	elapsedTime+= (t2.tv_usec - t1.tv_usec) / 1000.0;// µs to ms
	if(elapsedTime > m_Speed) {
		gettimeofday(&t1, NULL); // first time stamp
		if(m_Value > 0) {
			--m_Value;
			if(m_Value < 0)
				m_Value = 0;
			m_Value_1_Port->push(m_Value);
		}
	}
}
