/*
 * MixerSW.h
 *
 *  Created on: Jan 2, 2014
 *      Author: lukas
 */

#ifndef MIXERSW_H_
#define MIXERSW_H_

#include "../Mixer.h"

class Mixer_SW : public Mixer {
private:

    void getReadBuffer(int* readbuffer[MIXER_MAX_PORT_NUMBER]);
    void getBiasLevel(float* biasLevel);
public:
    Mixer_SW(std::vector<std::string> params);
    virtual ~Mixer_SW();

    void init();
    void process(void);

};

#endif /* MIXERSW_H_ */
