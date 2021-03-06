/*
 * SineImpl_SW.cpp
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */

#include "SineSoundComponent_SW.h"
#include "SineLookupTable.hpp"

double sine_lookup(double x)
{
	while (x < 0)
	{
		x += 2 * M_PI;
	}
	while (x > 2 * M_PI)
	{
		x -= 2 * M_PI;
	}

	double index = x / 0.0001;
	int indexi = (int) index;
	if (index - indexi > 0.5)
	{
		indexi++;
	}
	double value = sineTable[indexi];
	// possible improvement linearly interpolate between two values
	// not really necessary, the difference to the normal sine can't be heard anyway
	return value;
}

double my_sine(double x)
{
	// useful to pre-calculate
	double x2 = x * x;
	double x4 = x2 * x2;

	// Calculate the terms
	// As long as abs(x) < sqrt(6), which is 2.45, all terms will be positive.
	// Values outside this range should be reduced to [-pi/2, pi/2] anyway for accuracy.
	// Some care has to be given to the factorials.
	// They can be pre-calculated by the compiler,
	// but the value for the higher ones will exceed the storage capacity of int.
	// so force the compiler to use unsigned long longs (if available) or doubles.
	double t1 = x * (1.0 - x2 / (2 * 3));
	double x5 = x * x4;
	double t2 = x5 * (1.0 - x2 / (6 * 7)) / (1.0 * 2 * 3 * 4 * 5);
	double x9 = x5 * x4;
	double t3 = x9 * (1.0 - x2 / (10 * 11))
			/ (1.0 * 2 * 3 * 4 * 5 * 6 * 7 * 8 * 9);
	double x13 = x9 * x4;
	double t4 = x13 * (1.0 - x2 / (14 * 15))
			/ (1.0 * 2 * 3 * 4 * 5 * 6 * 7 * 8 * 9 * 10 * 11 * 12 * 13);
	// add some more if your accuracy requires them.
	// But remember that x is smaller than 2, and the factorial grows very fast
	// so I doubt that 2^17 / 17! will add anything.
	// Even t4 might already be too small to matter when compared with t1.

	// Sum backwards
	double result = t4;
	result += t3;
	result += t2;
	result += t1;

	return result;
}

SineSoundComponent_SW::SineSoundComponent_SW(std::vector<std::string> params) :
		SineSoundComponent(params)
{

	m_Phase = 0.0;
}

void SineSoundComponent_SW::process()
{

	if (this->m_active)
	{

		for (int i = 0; i < Synthesizer::config::blocksize; i++)
		{

			m_SoundOut_1_Port->writeSample(sine_lookup(m_Phase) * (1 << 30), i);  //  Q1.30

			m_Phase += m_PhaseIncr;

			if (m_Phase >= M_PI * 2)
			{
				m_Phase -= M_PI * 2;
			}
		}
	}
	else
	{
		for (int i = 0; i < Synthesizer::config::blocksize; i++)
		{
			m_SoundOut_1_Port->writeSample(0, i);

		}
	}

}

