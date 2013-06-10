/*
 * Copyright (c) 2009-2012 Xilinx, Inc.  All rights reserved.
 *
 * Xilinx, Inc.
 * XILINX IS PROVIDING THIS DESIGN, CODE, OR INFORMATION "AS IS" AS A
 * COURTESY TO YOU.  BY PROVIDING THIS DESIGN, CODE, OR INFORMATION AS
 * ONE POSSIBLE   IMPLEMENTATION OF THIS FEATURE, APPLICATION OR
 * STANDARD, XILINX IS MAKING NO REPRESENTATION THAT THIS IMPLEMENTATION
 * IS FREE FROM ANY CLAIMS OF INFRINGEMENT, AND YOU ARE RESPONSIBLE
 * FOR OBTAINING ANY RIGHTS YOU MAY REQUIRE FOR YOUR IMPLEMENTATION.
 * XILINX EXPRESSLY DISCLAIMS ANY WARRANTY WHATSOEVER WITH RESPECT TO
 * THE ADEQUACY OF THE IMPLEMENTATION, INCLUDING BUT NOT LIMITED TO
 * ANY WARRANTIES OR REPRESENTATIONS THAT THIS IMPLEMENTATION IS FREE
 * FROM CLAIMS OF INFRINGEMENT, IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE.
 *
 */

/*
 * helloworld.c: simple test application
 *
 * This application configures UART 16550 to baud rate 9600.
 * PS7 UART (Zynq) is not initialized by this application, since
 * bootrom/bsp configures it to baud rate 115200
 *
 * ------------------------------------------------
 * | UART TYPE   BAUD RATE                        |
 * ------------------------------------------------
 *   uartns550   9600
 *   uartlite    Configurable only in HW design
 *   ps7_uart    115200 (configured by bootrom/bsp)
 */

#include <stdio.h>
#include "platform.h"
#include "xbasic_types.h"
#include "time_base.h"
#include "helloworld.h"

/**
Variables
**/
void print(char *str);
Xuint32 *freq;

/**
Procedures
**/
void initialize()
{
	freq = (Xuint32 *) SOUNDGATES_BASEADDR;
	*(freq) = 0;
}

float getFreqFromNote(float x)
{ 
	float f1 = pow(2,(x/12));
	float f2 = 8.1758*f1;
	return f2;
}

void changeFrequence(Xuint32 freq)
{
	*freq = freq;
}

int main()
{
    init_platform();
    print("Hello World\n\r");
    int i;
    int size = sizeof(notes) / sizeof(notes[0]);
    int lastOfftime = 0;

    while(1){
    for(i=0; i<size; i++)  {
        struct midiNote currentMidiNote = notes[i];
		int pauseDuration = currentMidiNote.ontime - lastOfftime;
        int playDuration = currentMidiNote.offtime - currentMidiNote.ontime;

        changeFrequence(0);
        wait_for_ms(pauseDuration);
		changeFrequence(currentMidiNote.note);
        wait_for_ms(playDuration);

        lastOfftime=currentMidiNote.offtime;
      }
    }
    return 0;
}

/*
Xuint32 sleep(Xuint32 ms)
{
	int i;
	int j;
	int k;
	for(i; i<ms; i++)
	{
		for(j; j<20000000; j++)
		{
			k = i+j;
		}
	}
	return k;
}
*/
