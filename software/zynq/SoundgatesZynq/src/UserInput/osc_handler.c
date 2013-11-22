#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include "lo/lo.h"

#include "osc_handler.h"
#include "../ComponentStructs.h"
#include "../Inputconverter.h"

int done = 0;
sOSCComponent *components;

void error(int num, const char *msg, const char *path)
{
    printf("liblo server error %d in path %s: %s\n", num, path, msg);
    fflush(stdout);
}

/**
 *
 * TODO: Replace types with structs. Some components need more than
 * one value :(
 */
void write_input_value(int id, float value, void* value_address)
{
		if(id == ID_BIAS || id == ID_BEAT)
			*(float*)value_address = value;
		else
		{
			int increment = freq_to_incr(id, value);
			*((int*)value_address) = increment;
		}
}

/* handle soundgates messages */
int soundgates_handler(const char *path, const char *types, lo_arg ** argv,
                int argc, void *data, void *user_data)
{
    /* example showing pulling the argument values out of the argv array */
	if(argc != 1)
	{
		printf("Error: Didnt recognize number of types. \n");
		fflush(stdout);
		return 0;
	}
    sOSCComponent *iterator = components;
    while(iterator)
    {
    	if(! strcmp(iterator->comp_osc_name, path))
		{
    		switch(*types)
    		{
    		case 'i':
        		printf("Receiving: %s with value: %i \n", iterator->comp_osc_name,argv[0]->i);
        		write_input_value(iterator->comp_id, argv[0]->i, iterator->comp_value_pointer);
    			break;
    		case 'f':
        		printf("Receiving: %s with value: %f \n", iterator->comp_osc_name,argv[0]->f);
        		write_input_value(iterator->comp_id, argv[0]->f, iterator->comp_value_pointer);
    			break;
    		default:
    			printf("Error: Unrecognized type %c", *types);
    		}

    		fflush(stdout);
   		}
    	iterator = (sOSCComponent*) iterator->next;
    }
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

void* osc_handler_thread(void *args)
{
	printf("Creating osc_handler_thread...\n");
	fflush(stdout);
    char port[6];
    sprintf(port, "%d", PORT);

    /* create new server thread that listens on PORT (UDP) */
    lo_server_thread st = lo_server_thread_new(port, error);

	components = (sOSCComponent*)args;

	lo_server_thread_add_method(st, NULL, NULL, &soundgates_handler, NULL );
    
    /* add method that will match the path /quit with no args */
    lo_server_thread_add_method(st, "/quit", "", quit_handler, NULL);
    
    /* start the server thread */
    lo_server_thread_start(st);

    while (!done) {
#ifdef WIN32
        Sleep(1);
#else
        usleep(1000);
#endif
    }
    printf("I am done \n");

    lo_server_thread_free(st);
    
    return 0;
}

