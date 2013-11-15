#ifndef OSC_HANDLER_H
#define OSC_HANDLER_H

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include "lo/lo.h"

#include "user_input.h"


void error(int, const char *, const char *);

int generic_handler(const char *, const char *, lo_arg **, int, void *, void *);

int foo_handler(const char *, const char *, lo_arg **, int, void *, void *);

int quit_handler(const char *, const char *, lo_arg **, int, void *, void *);

void* osc_handler_thread(void *);

#endif
