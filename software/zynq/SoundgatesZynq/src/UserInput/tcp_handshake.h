#ifndef TCP_HANDSHAKE_H
#define TCP_HANDSHAKE_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>

#include "user_input.h"


char** getInteractiveComponents(unsigned int *); 

void error_handler(const char *);

void send_all(int, void *, size_t);

void tcp_client_handler(int);

void* tcp_handshake_thread(void *);

#endif
