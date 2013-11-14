// calculates the frequency of sawtooth and triangle (not square and sinus!) into its phase increment value
float freq_to_incr (float freq)
{
    return freq * 2.0f / 1000.0f;
}

// calculates the frequency of square into its phase increment value, depending on duty cycle ratio
// duty_cycle = signal_ON_time / signal_OFF_time
// duty cycle standard = 1
float freq_to_incr_sq (float freq, float duty_ratio)
{
    float duty_on  = duty_ratio;
    float duty_off = duty_on + 1;
    return freq * (duty_off) / 1000.0f;
}
