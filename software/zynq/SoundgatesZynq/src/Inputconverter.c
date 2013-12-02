#include <math.h>

#include "Inputconverter.h"
#include "ComponentStructs.h"
#include "SynthesizerCommon.h"

// returns increment values for wave generators (id 1 to 4)
int freq_to_incr (int comp_id, float freq)
{
    switch (comp_id)
    {
        case ID_SIN: // Sinus
            return ((M_PI * 2 * freq) / SAMPLE_RATE) * SOUNDGATES_FIXED_PT_SCALE;
        case ID_SAW: // Sawtooth
            return (int) (freq * 2 ) / SAMPLE_RATE * SOUNDGATES_FIXED_PT_SCALE;
            
        case ID_TRI: // Triangle
            return (int) ((4 * freq* SOUNDGATES_FIXED_PT_SCALE) / SAMPLE_RATE);
            
        case ID_SQR: // Square
            return (int) (freq * 2 ) / SAMPLE_RATE * SOUNDGATES_FIXED_PT_SCALE;            // TODO: duty cycle
            
        default:
            return 0;
    }
    return 0;
}

// returns increment values for the adsr depending on height of attack, sustain and release phase
int ms_to_attack (int ms, float attack_height)
{
    return ((attack_height * (1 / (ms / 100))) / SAMPLE_RATE) * SOUNDGATES_FIXED_PT_SCALE;
}

int ms_to_sustain (int ms, float attack_height, float sustain_height)
{
   return (((attack_height - sustain_height) * (1 / (ms / 100))) / SAMPLE_RATE) * SOUNDGATES_FIXED_PT_SCALE;
}

int ms_to_release (int ms, float sustain_height, float release_height)
{
    return (((sustain_height - release_height) * (1 / (ms / 100))) / SAMPLE_RATE) * SOUNDGATES_FIXED_PT_SCALE;
}

