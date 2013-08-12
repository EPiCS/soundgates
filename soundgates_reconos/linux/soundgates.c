#include "reconos.h"
#include "mbox.h"

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> 
#include <fcntl.h>
#include <unistd.h>


// hw thread
struct reconos_hwt hwt;
struct reconos_resource res[1];

// message boxes
struct mbox  mb_comm;


int main(int argc, char ** argv)
{
	uint32_t message = 440;
	uint32_t answer = 0;
	mbox_init(&mb_comm,1);
	
	reconos_cache_flush();
	reconos_init_autodetect();
	reconos_cache_flush();
	
	res[0].type = RECONOS_TYPE_MBOX;
	res[0].ptr  = &mb_comm;
	
	//res[0].type = RECONOS_TYPE_SEM;
	//res[0].ptr  = &sem_a;
	
	//sem_init(&sem_a,0,0);
	//sem_init(&sem_b,0,0);
	
	reconos_hwt_setresources(&hwt,res,1);
	reconos_hwt_setinitdata(&hwt, (void *)message);
	reconos_hwt_create(&hwt,0,NULL);
  
	answer = mbox_get( &mb_comm );
 	printf("Response is: %lu \n", ( unsigned long )answer);
 	
  	while(1)
  	{
  			sleep(5);
  		 	printf("Response is: %lu \n", ( unsigned long )answer);	
  	}
	
	return 0;
}

