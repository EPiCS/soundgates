
// calculates the frequency of sawtooth, triangle and square wave (not sinus!) into its phase increment value

float freq_to_incr (float freq)
{
    return freq * 2.0f / 1000.0f;
}

