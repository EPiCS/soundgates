#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include <sys/socket.h>
#include <arpa/inet.h>

#include <pthread.h>

#include "lo/lo.h"

#define PORT                50050
#define MAXPENDING          5
#define BUFSIZE             200
#define OSC_MSG_DELIMITER   "||"

char* interactiveComponents[] = {
    "/component1 \"f\"",
    "/component2 \"i\"",
    "/quit"
};
    
// TODO: Pack array of components and #components into struct?!
char** getInteractiveComponents(unsigned int *numInteractiveComponents)
{
    *numInteractiveComponents = 3;
    return interactiveComponents;
} 

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

void send_all(int clientSock, void *buf, size_t length)
{
    size_t i = 0;
    while(i < length)
        i += send(clientSock, buf + 0, length - i, 0);
}

void tcp_client_handler(int clientSock)
{
    char* recvMsgBuf = malloc((BUFSIZE+1) * sizeof(char));
    char* sendMsgBuf;
    int recvMsgSize;
    int sendMsgSize;

    /* Receive message from client */
    if ((recvMsgSize = recv(clientSock, recvMsgBuf, BUFSIZE, 0)) < 0)
        error_handler("recv() failed");
    
    /* Terminate received message  */
    recvMsgBuf[recvMsgSize] = '\0';
    
    printf("Received: \"%s\" (%d bytes)\n", recvMsgBuf, recvMsgSize);    
    
    if (recvMsgSize > 0)
    {
        if(strncmp("getInteractiveComponents", recvMsgBuf, strlen("getInteractiveComponents")) == 0)
        {
            unsigned int numInteractiveComponents, i;
            char **components = getInteractiveComponents(&numInteractiveComponents);
            
            /* sum up the length of the OSC component descriptions */
            for(i = 0, sendMsgSize = 0; i < numInteractiveComponents; ++i)
                sendMsgSize += strlen(components[i]);
            
            /* add the length of n-1 delimiters */
            sendMsgSize += (numInteractiveComponents-1) * strlen(OSC_MSG_DELIMITER);
            
            /* add 1 for the trailing '\n' */
            sendMsgSize += 1;
            
            sendMsgBuf = malloc(sendMsgSize * sizeof(char) + 1);
            
            for(i = 0; i < numInteractiveComponents; ++i)
            {
                strncat(sendMsgBuf, components[i], strlen(components[i]));
                if(i != numInteractiveComponents-1)
                    strncat(sendMsgBuf, OSC_MSG_DELIMITER, strlen(OSC_MSG_DELIMITER));
                else
                    strncat(sendMsgBuf, "\n", 1);
            }
            
            fprintf(stdout, "Sending: \"%s\"\n", sendMsgBuf);
            send_all(clientSock, sendMsgBuf, sendMsgSize);
            
        }
        else
        {
            sendMsgBuf = malloc(BUFSIZE * sizeof(char));
            sendMsgSize = snprintf(sendMsgBuf, BUFSIZE, "Unknown command:\"%s\"\n", recvMsgBuf);
            fprintf(stderr, "%s", sendMsgBuf);
            send_all(clientSock, sendMsgBuf, sendMsgSize);
        }
    }

    close(clientSock);
}

void *tcp_handshake_thread(void *args)
{
    int serverSock;                 /* Socket descriptor for server            */
    int clientSock;                 /* Socket descriptor for client            */
    struct sockaddr_in serverAddr;  /* Server/Local  address                   */
    struct sockaddr_in clientAddr;  /* Client/Remote address                   */
    unsigned int clientAddrLen;     /* Length of client address data structure */

    /* Create socket for incoming connections */
    if ((serverSock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0)
        error_handler("socket() failed");
      
    /* Construct local address structure */
    memset(&serverAddr, 0, sizeof(serverAddr));           /* Zero out structure */
    serverAddr.sin_family         = AF_INET;              /* Internet address family */
    serverAddr.sin_addr.s_addr    = htonl(INADDR_ANY);    /* Any incoming interface */
    serverAddr.sin_port           = htons(PORT);          /* Local port */

    /* Bind to the local address */
    if (bind(serverSock, (struct sockaddr *) &serverAddr, sizeof(serverAddr)) < 0)
        error_handler("bind() failed");

    /* Mark the socket so it will listen for incoming connections */
    if (listen(serverSock, MAXPENDING) < 0)
        error_handler("listen() failed");

    for (;;) /* Run forever */
    {
        /* Set the size of the in-out parameter */
        clientAddrLen = sizeof(clientAddr);

        /* Wait for a client to connect */
        if ((clientSock = accept(serverSock, (struct sockaddr *) &clientAddr, &clientAddrLen)) < 0)
            error_handler("accept() failed");

        printf("Handling client %s\n", inet_ntoa(clientAddr.sin_addr));

        tcp_client_handler(clientSock);
    }
    /* NOT REACHED */
}

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
