#ifndef INPUTCONVERTER_H
#define INPUTCONVERTER_H

#include "ComponentStructs.h"
#include <math.h>

#define SOUNDGATES_FIXED_PT_SCALE (1 << 27)
#define FPGA_REFERENCE_FREQUENCY  100000000
#define SAMPLE_RATE               44100

// returns increment values for wave generators (id 1 to 4)
int freq_to_incr (int comp_id, float freq)
{
    switch (comp_id)
    {
        case ID_SIN: // Sinus
            //return ((M_PI * 2 * freq) / SAMPLE_RATE) * SOUNDGATES_FIXED_PT_SCALE;
        	return ((4 * freq) * SOUNDGATES_FIXED_PT_SCALE/ SAMPLE_RATE) ;
        case ID_SAW: // Sawtooth - correct calculation?
            return (freq * 2 ) / SAMPLE_RATE * SOUNDGATES_FIXED_PT_SCALE;
            
        case ID_TRI: // Triangle - correct calculation?
            return ((4 * freq* SOUNDGATES_FIXED_PT_SCALE) / SAMPLE_RATE);
            
        case ID_SQR: // Square   - correct calculation?
            return (freq * 2 ) / SAMPLE_RATE * SOUNDGATES_FIXED_PT_SCALE;            // TODO: duty cycle
            
        default:
            return 0;
    }
    return 0;
}

#endif
