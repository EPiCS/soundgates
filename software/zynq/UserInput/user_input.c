#include "user_input.h"

int done = 0;

int main()
{
    pthread_t pthread_t_tcp_handshake, pthread_t_osc_handler;
    int tcp_ret, osc_ret;
    
    tcp_ret = pthread_create(&pthread_t_tcp_handshake, NULL, tcp_handshake_thread, (void*)NULL);
    osc_ret = pthread_create(&pthread_t_osc_handler, NULL, osc_handler_thread, (void*)NULL);
    
    /* wait for threads before return */
    pthread_join(pthread_t_tcp_handshake, NULL);
    pthread_join(pthread_t_osc_handler, NULL);
    
    printf("TCP Handshake thread returns: %d\n",tcp_ret);
    printf("OSC Handler thread returns: %d\n",osc_ret);
    return 0;
}


