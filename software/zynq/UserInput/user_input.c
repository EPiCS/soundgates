#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include <sys/socket.h>
#include <arpa/inet.h>

#include <pthread.h>

#include "lo/lo.h"

#define PORT        50050
#define MAXPENDING  5
#define RCVBUFSIZE  50

void error_handler(const char *msg)
{
    #ifdef _WIN32
	LPVOID lpMsgBuf = (LPVOID)"Unknown error";
	int e = WSAGetLastError();
	if (FormatMessage(FORMAT_MESSAGE_ALLOCATE_BUFFER | FORMAT_MESSAGE_FROM_SYSTEM | FORMAT_MESSAGE_IGNORE_INSERTS,
                      NULL, e, MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT) /* default language */, (LPTSTR)&lpMsgBuf, 0, NULL)
        ) {
		fprintf(stderr, "%s: Error %d: %s\n", msg, e, lpMsgBuf);
		LocalFree(lpMsgBuf);
	} else
		fprintf(stderr, "%s: Error %d\n", msg, e);
    #else
    perror (msg);
    #endif
    exit(-1);
}

void client_handler(int clntSocket)
{
    char echoBuffer[RCVBUFSIZE+1];        /* Buffer for echo string */
    int recvMsgSize;                    /* Size of received message */
    int sendMsgSize;

    /* Receive message from client */
    if ((recvMsgSize = recv(clntSocket, echoBuffer, RCVBUFSIZE-1, 0)) < 0)
        error_handler("recv() failed");
        
    printf("\"%s\"(%d) empfangen\n", echoBuffer, recvMsgSize);
    /* Send received string and receive again until end of transmission */
    if (recvMsgSize > 0)      /* zero indicates end of transmission */
    {
        echoBuffer[recvMsgSize] = '\n';
        echoBuffer[recvMsgSize+1] = '\0';
        /* Echo message back to client */
        sendMsgSize = send(clntSocket, echoBuffer, recvMsgSize+1, 0);
        if (sendMsgSize != recvMsgSize+1)
            error_handler("send() failed");
        printf("\"%s\"(%d) gesendet\n", echoBuffer, sendMsgSize);
        /* See if there is more data to receive */
        //if ((recvMsgSize = recv(clntSocket, echoBuffer, RCVBUFSIZE, 0)) < 0)
        //    error_handler("recv() failed");
    }

    close(clntSocket);    /* Close client socket */
}

void *tcp_handshake_thread(void *args)
{
    int servSock;                    /* Socket descriptor for server */
    int clntSock;                    /* Socket descriptor for client */
    struct sockaddr_in echoServAddr; /* Server/Local address */
    struct sockaddr_in echoClntAddr; /* Client/Remote address */
    unsigned int clntLen;            /* Length of client address data structure */

    /* Create socket for incoming connections */
    if ((servSock = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0)
        error_handler("socket() failed");
      
    /* Construct local address structure */
    memset(&echoServAddr, 0, sizeof(echoServAddr));         /* Zero out structure */
    echoServAddr.sin_family         = AF_INET;              /* Internet address family */
    echoServAddr.sin_addr.s_addr    = htonl(INADDR_ANY);    /* Any incoming interface */
    echoServAddr.sin_port           = htons(PORT);          /* Local port */

    /* Bind to the local address */
    if (bind(servSock, (struct sockaddr *) &echoServAddr, sizeof(echoServAddr)) < 0)
        error_handler("bind() failed");

    /* Mark the socket so it will listen for incoming connections */
    if (listen(servSock, MAXPENDING) < 0)
        error_handler("listen() failed");

    for (;;) /* Run forever */
    {
        /* Set the size of the in-out parameter */
        clntLen = sizeof(echoClntAddr);

        /* Wait for a client to connect */
        if ((clntSock = accept(servSock, (struct sockaddr *) &echoClntAddr, &clntLen)) < 0)
            error_handler("accept() failed");

        /* clntSock is connected to a client! */

        printf("Handling client %s\n", inet_ntoa(echoClntAddr.sin_addr));

        client_handler(clntSock);
    }
    /* NOT REACHED */
}

int main()
{
    pthread_t pthread_t_tcp_handshake;
    int iret;
    
    iret = pthread_create(&pthread_t_tcp_handshake, NULL, tcp_handshake_thread, (void*)NULL);
    
    pthread_join(pthread_t_tcp_handshake, NULL);
    
    printf("TCP Handshake thread returns: %d\n",iret);
    return 0;
}

int done = 0;

void error(int num, const char *m, const char *path);

int generic_handler(const char *path, const char *types, lo_arg ** argv,
                    int argc, void *data, void *user_data);

int foo_handler(const char *path, const char *types, lo_arg ** argv,
                int argc, void *data, void *user_data);

int quit_handler(const char *path, const char *types, lo_arg ** argv,
                 int argc, void *data, void *user_data);

int lo_main()
{
    /* start a new server on port 7770 */
    lo_server_thread st = lo_server_thread_new("7770", error);

    /* add method that will match any path and args */
    lo_server_thread_add_method(st, NULL, NULL, generic_handler, NULL);

    /* add method that will match the path /foo/bar, with two numbers, coerced
     * to float and int */
    lo_server_thread_add_method(st, "/foo/bar", "fi", foo_handler, NULL);

    /* add method that will match the path /quit with no args */
    lo_server_thread_add_method(st, "/quit", "", quit_handler, NULL);

    lo_server_thread_start(st);

    while (!done) {
#ifdef WIN32
        Sleep(1);
#else
        usleep(1000);
#endif
    }

    lo_server_thread_free(st);

    return 0;
}

void error(int num, const char *msg, const char *path)
{
    printf("liblo server error %d in path %s: %s\n", num, path, msg);
    fflush(stdout);
}

/* catch any incoming messages and display them. returning 1 means that the
 * message has not been fully handled and the server should try other methods */
int generic_handler(const char *path, const char *types, lo_arg ** argv,
                    int argc, void *data, void *user_data)
{
    int i;

    printf("path: <%s>\n", path);
    for (i = 0; i < argc; i++) {
        printf("arg %d '%c' ", i, types[i]);
        lo_arg_pp((lo_type)types[i], argv[i]);
        printf("\n");
    }
    printf("\n");
    fflush(stdout);

    return 1;
}

int foo_handler(const char *path, const char *types, lo_arg ** argv,
                int argc, void *data, void *user_data)
{
    /* example showing pulling the argument values out of the argv array */
    printf("%s <- f:%f, i:%d\n\n", path, argv[0]->f, argv[1]->i);
    fflush(stdout);

    return 0;
}

int quit_handler(const char *path, const char *types, lo_arg ** argv,
                 int argc, void *data, void *user_data)
{
    done = 1;
    printf("quiting\n\n");
    fflush(stdout);

    return 0;
}
