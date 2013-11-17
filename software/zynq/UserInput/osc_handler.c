#include "osc_handler.h"

extern int done;

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

int sinefreq_integer_handler(const char *path, const char *types, lo_arg ** argv,
                int argc, void *data, void *user_data)
{
    int value = argv[0]->i;
    // TODO: put value to sine generator
    
    printf("%s <- i:%d\n\n", path, value);
    fflush(stdout);

    return 0;
}

int trianglefreq_integer_handler(const char *path, const char *types, lo_arg ** argv,
                int argc, void *data, void *user_data)
{
    int value = argv[0]->i;
    // TODO: put value to triangle generator
    
    printf("%s <- i:%d\n\n", path, value);
    fflush(stdout);

    return 0;
}

int mixer1bias_float_handler(const char *path, const char *types, lo_arg ** argv,
                int argc, void *data, void *user_data)
{
    float value = argv[0]->f;
    // TODO: put value to sine generator
    
    printf("%s <- f:%f\n\n", path, value);
    fflush(stdout);

    return 0;
}

int mixer2bias_float_handler(const char *path, const char *types, lo_arg ** argv,
                int argc, void *data, void *user_data)
{
    float value = argv[0]->f;
    // TODO: put value to sine generator
    
    printf("%s <- f:%f\n\n", path, value);
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

void* osc_handler_thread(void *args)
{
    char port[6];
    sprintf(port, "%d", PORT);
    
    /* create new server thread that listens on PORT (UDP) */
    lo_server_thread st = lo_server_thread_new(port, error);

    /* add method that will match any path and args */
    //lo_server_thread_add_method(st, NULL, NULL, generic_handler, NULL);
    
    lo_server_thread_add_method(st, "/sinefreq", "i", sinefreq_integer_handler, NULL);
    lo_server_thread_add_method(st, "/trianglefreq", "i", trianglefreq_integer_handler, NULL);
    lo_server_thread_add_method(st, "/mixer1bias", "f", mixer1bias_float_handler, NULL);
    lo_server_thread_add_method(st, "/mixer2bias", "f", mixer2bias_float_handler, NULL);
    
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

    lo_server_thread_free(st);
    
    return 0;
}
