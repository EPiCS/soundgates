#include "user_input.h"
#include "SynthesizerTest.h"

float offset_sin;

float offset_tri;

int[2] osc_ret; // osc value

int main()
{
 
//----------------------  
// TODO: TO SYNTHESIZER:
    pthread_t pthread_t_osc_handler;
    int osc_ret;
    
    osc_ret = pthread_create(&pthread_t_osc_handler, NULL, osc_handler_thread, (void*)NULL);
// --------------------

}


