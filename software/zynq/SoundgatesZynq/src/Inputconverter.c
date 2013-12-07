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

// returns increment values for the adsr depending on amplitudes of attack, sustain and release phase
int ms_to_attack (int ms, float attack_amp)
{
    return ((attack_amp * (100 / ms)) / SAMPLE_RATE) * SOUNDGATES_FIXED_PT_SCALE;
}

int ms_to_sustain (int ms, float attack_amp, float sustain_amp)
{
   return (((attack_amp - sustain_amp) * (100 / ms)) / SAMPLE_RATE) * SOUNDGATES_FIXED_PT_SCALE;
}

int ms_to_release (int ms, float sustain_height, float release_amp)
{
    return (((sustain_amp - release_amp) * (100 / ms)) / SAMPLE_RATE) * SOUNDGATES_FIXED_PT_SCALE;
}

// returns increment values for ramp envelope depending on amplitudes
int ms_to_incr ( int ms, float ramp_amp, float start_stop_amp)
{
    return (((ramp_amp - start_amp) * (100 / ms)) / SAMPLE_RATE) * SOUNDGATES_FIXED_PT_SCALE;
}


// frequency control for a NCO
// calculates increment out of wave output, dependant on min and max frequency
// 
// function:
//      f(wave, min, max) = (max-min)/2^28 *(wave*2) + min
//
// usage (control wave_1 via wave_0):
//      wave_1_increment = wave_to_incr(wave_0_output, comp_id[wave_0], 220, 440)    
// 
float wave_to_incr (int wave, int comp_id, min_freq, max_freq)
{
    return freq_to_incr(comp_id, (max-min)/2^SOUNDGATES_FIXED_PT_SCALE *(wave*2) + min);
}

