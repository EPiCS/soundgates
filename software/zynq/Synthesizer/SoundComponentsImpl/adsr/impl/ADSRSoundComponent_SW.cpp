/*
 * ADSRImplSW.cpp
 *
 *  Created on: Dec 10, 2013
 *      Author: lukas
 */

#include "ADSRSoundComponent_SW.h"

ADSRSoundComponent_SW::ADSRSoundComponent_SW(std::vector<std::string> params) : ADSRSoundComponent(params){

    m_SamplesProcessed = 0;
    m_ADSRState = IDLE;

}

ADSRSoundComponent_SW::~ADSRSoundComponent_SW() {}


void ADSRSoundComponent_SW::init(){ /* nothing to do */}


void ADSRSoundComponent_SW::process(){

	char* readptr  = ((BufferedLink*)m_SoundInPort->getLink())->getReadBuffer();
	char* writeptr = ((BufferedLink*)m_SoundOutPort->getLink())->getWriteBuffer();

	float sustainlvl = ((ControlLink*)m_SustainPort->getLink())->getNextControlData();

	int attacksamplecount  = (int) (Synthesizer::config::samplerate * ((ControlLink*)m_AttackPort->getLink())->getNextControlData());
	int decaysamplecount   = (int) (Synthesizer::config::samplerate * ((ControlLink*)m_DecayPort->getLink())->getNextControlData());
	int releasesamplecount = (int) (Synthesizer::config::samplerate * ((ControlLink*)m_ReleasePort->getLink())->getNextControlData());

	int trigger	= (int) ((ControlLink*)m_TriggerPort->getLink())->getNextControlData();

	int i,j;
	int blockSamplesProcessed = 0;
	float curr_lvl;

	while(blockSamplesProcessed < Synthesizer::config::blocksize){

		switch(m_ADSRState){

		case IDLE:

			memset(writeptr, 0, Synthesizer::config::blocksize * sizeof(int));

			blockSamplesProcessed = Synthesizer::config::blocksize;

			if(trigger){
				m_SamplesProcessed = 0;
				m_ADSRState	= ATTACK;
			}else{
				m_ADSRState = IDLE;
			}

			break;
		case ATTACK:
			for (i = blockSamplesProcessed; i < Synthesizer::config::blocksize;	i++, m_SamplesProcessed++, blockSamplesProcessed++) {

				((int*) writeptr)[i] = ((int*) readptr)[i] * ((float) m_SamplesProcessed / attacksamplecount);

				if(m_SamplesProcessed >= attacksamplecount){

					m_SamplesProcessed = 0;
					m_ADSRState = DECAY;

					break;
				}
			}

			break;
		case DECAY:

			for (i = blockSamplesProcessed; i < Synthesizer::config::blocksize; i++, m_SamplesProcessed++, blockSamplesProcessed++) {

				curr_lvl = 1.0 - ((float) m_SamplesProcessed / decaysamplecount);

				if (curr_lvl < sustainlvl) {

					curr_lvl = sustainlvl;
				}

				((int*) writeptr)[i] = ((int*) readptr)[i] * curr_lvl;

				if(m_SamplesProcessed >= decaysamplecount){
					m_SamplesProcessed = 0;
					m_ADSRState = SUSTAIN;
					break;
				}
			}

			break;
		case SUSTAIN:

			for(i = blockSamplesProcessed; i < Synthesizer::config::blocksize; i++, blockSamplesProcessed++){
				((int*)writeptr)[i] = ((int*)readptr)[i] * sustainlvl;

				trigger = (int) ((ControlLink*)m_TriggerPort->getLink())->getNextControlData();

				if (!trigger) {
					m_SamplesProcessed = 0;
					m_ADSRState = RELEASE;
				} else {
					m_ADSRState = SUSTAIN;
				}
			}
			break;
		case RELEASE:

			for (i = blockSamplesProcessed; i < Synthesizer::config::blocksize;	i++, m_SamplesProcessed++, blockSamplesProcessed++) {

				curr_lvl = ((float) m_SamplesProcessed / releasesamplecount);

				((int*) writeptr)[i] = (((int*) readptr)[i] * sustainlvl) * (1.0 - curr_lvl);


				if (m_SamplesProcessed >= releasesamplecount) {

					for (j = i; j < Synthesizer::config::blocksize;	j++){

						((int*) writeptr)[j] = 0;
					}
					blockSamplesProcessed = Synthesizer::config::blocksize;
					m_SamplesProcessed = 0;
					m_ADSRState = IDLE;
					break;
				}
			}
			break;
		}
	}
}
