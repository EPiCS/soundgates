/*
 * TriangleSoundComponent_SW.cpp
 *
 */

#include "TriangleSoundComponent_SW.h"

TriangleSoundComponent_SW::TriangleSoundComponent_SW(
		std::vector<std::string> params) :
		TriangleSoundComponent(params)
{

	m_Phase = 0.0;
}

void TriangleSoundComponent_SW::process()
{

	if (this->m_active)
	{

		for (int i = 0; i < Synthesizer::config::blocksize; i++)
		{
			double val;
			if (m_Phase <= M_PI)
				val = (double)INT_MIN + ((double)INT_MAX - INT_MIN) * ((m_Phase - 0) / M_PI - 0);
			else
				val = ((double) INT_MAX
						+ ((double) INT_MIN - INT_MAX)
								* ((m_Phase - M_PI) / (2 * M_PI - M_PI)));

			m_SoundOut_1_Port->writeSample(val, i); //  Q1.30

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

