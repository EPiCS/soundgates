#ifndef INTERACTIVECOMPONENTS_H
#define INTERACTIVECOMPONENTS_H

typedef struct{
    int numOscMsg;
    char** oscMsg; 
} components;

components* getInteractiveComponents(void);

#endif
