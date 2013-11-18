#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include "lo/lo.h"

#include "../../include/osc_handler.h"
#include "ComponentStructs.h"
#include "Inputconverter.h"

int done = 0;
sOSCComponent *components;

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

    printf("Error: path: <%s> not found. \n", path);
    for (i = 0; i < argc; i++) {
        printf("arg %d '%c' ", i, types[i]);
        lo_arg_pp((lo_type)types[i], argv[i]);
        printf("\n");
    }
    printf("\n");
    fflush(stdout);

    return 1;
}

/**
 *
 * TODO: Replace types with structs. Some components need more than
 * one value :(
 */
void write_input_value(int id, float value, void* value_address)
{
		if(id == ID_BIAS)
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
    float output = argv[0]->f;
    sOSCComponent *iterator = components;
    while(iterator)
    {
    	if(! strcmp(iterator->comp_osc_name, path))
		{
    		printf("Receiving: %s with value: %f \n", iterator->comp_osc_name,output);
    		write_input_value(iterator->comp_id, output, iterator->comp_value_pointer);
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
	sOSCComponent *iterator = components;
	while(iterator)
	{
		printf("Adding handler %s \n", iterator->comp_osc_name);
		lo_server_thread_add_method(st, iterator->comp_osc_name , "f", &soundgates_handler, NULL);
		iterator = (sOSCComponent*) iterator->next;
		fflush(stdout);
	}

    //add method that will match any path and args
    lo_server_thread_add_method(st, NULL, NULL, generic_handler, NULL);
    
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

