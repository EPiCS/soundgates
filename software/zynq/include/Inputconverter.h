#ifndef INPUTCONVERTER_H
#define INPUTCONVERTER_H

#include "ComponentStructs.h"
#include <math.h>

#define SOUNDGATES_FIXED_PT_SCALE (1 << 27)
#define FPGA_REFERENCE_FREQUENCY  100000000
#define SAMPLE_RATE               44100

// returns increment values for wave generators (id 1 to 4)
float freq_to_incr (int comp_id, float freq)
{
    switch (comp_id)
    {
        case 1: // Sinus
            return (freq * 2 * M_PI) / SAMPLE_RATE * SOUNDGATES_FIXED_PT_SCALE;
            
        case 2: // Sawtooth - correct calculation?
            return (freq * 2 ) / SAMPLE_RATE * SOUNDGATES_FIXED_PT_SCALE;
            
        case 3: // Triangle - correct calculation?
            return (freq * 4 ) / SAMPLE_RATE * SOUNDGATES_FIXED_PT_SCALE;
            
        case 4: // Square   - correct calculation?
            return (freq * 2 ) / SAMPLE_RATE * SOUNDGATES_FIXED_PT_SCALE;            // TODO: duty cycle
            
        default:
            return 0;
    }
    return 0;
}

#endif
