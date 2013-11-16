#include "user_input.h"
#include "SynthesizerTest.h"

float offset_sin;

float offset_tri;

int[2] osc_ret; // osc value

int main()
{
    // TODO: offsets abfangen
    offset_sin = 0;
    offset_tri = 0;
   
// TODO: TO SYNTHESIZER:
    osc_ret* = pthread_create(&pthread_t_osc_handler, NULL, osc_handler_thread, (void*)NULL);
// ----

    if (osc_ret[0] == SINUS) {
        // send frequency
        set_frequency_sin(osc_ret[1], offset_sin);
    } else if (osc_ret[0] == TRIANGLE) {
        // send frequency
        set_frequency_tri(osc_ret[1], offset_tri);
    } else if (osc_ret[0] == SINUS) {
        // TODO 
    }

    set_frequency_sin(freq_sin, offset_sin);
    set_frequency_tri(freq_tri, offset_tri);    
   
    return 0;

/*  pthread_t pthread_t_tcp_handshake, pthread_t_osc_handler;
    int tcp_ret, osc_ret;
    
    tcp_ret = pthread_create(&pthread_t_tcp_handshake, NULL, tcp_handshake_thread, (void*)NULL);
    osc_ret = pthread_create(&pthread_t_osc_handler, NULL, osc_handler_thread, (void*)NULL);
    
    /* wait for threads before return 
    pthread_join(pthread_t_tcp_handshake, NULL);
    pthread_join(pthread_t_osc_handler, NULL);
    
    printf("TCP Handshake thread returns: %d\n",tcp_ret);
    printf("OSC Handler thread returns: %d\n",osc_ret);
    return 0;

*/ 
}


