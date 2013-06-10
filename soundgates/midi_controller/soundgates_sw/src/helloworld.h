#include <stdio.h>
//#include "platform.h"
#include "xbasic_types.h"

struct midiNote
{
	int ontime;
	int offtime;
	int note;
};

/*
	Star Wars Theme
*/

struct midiNote notes[66];
