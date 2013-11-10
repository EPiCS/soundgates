#include "user_input.h"

int main()
{
    pthread_t pthread_t_tcp_handshake;
    int iret;
    
    iret = pthread_create(&pthread_t_tcp_handshake, NULL, tcp_handshake_thread, (void*)NULL);
    
    /* wait for tcp handshake thread before return */
    pthread_join(pthread_t_tcp_handshake, NULL);
    
    printf("TCP Handshake thread returns: %d\n",iret);
    return 0;
}


