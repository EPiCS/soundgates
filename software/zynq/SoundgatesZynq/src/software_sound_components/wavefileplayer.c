/*
 * wavefileplayer.c
 *
 *  Created on: 11.11.2013
 *      Author: gwue
 */

#include "wavefileplayer.h"

wavefileplayer* wavefileplayer_create_from_file(FILE* fp, int loop)
{
	wavefileplayer* wfp = malloc(sizeof(wavefileplayer));

	fseek(fp, 0l, SEEK_END);
	wfp->arraysize = ftell(fp);
	fseek(fp, 0l, SEEK_SET);

	wfp->data = malloc(wfp->arraysize);
	wfp->loop = loop;
	wfp->offset = 0;
	long int i = 0;
	while (i < wfp->arraysize)
	{
		unsigned char mychar = fgetc(fp);
		wfp->data[i] = mychar;
		i++;
	}
	//TODO Strip the WAVE Header
	//TODO Maybe check the Header if it is encoded in the way we want it (or maybe even transform it into 44100 Hz, 32 Bit unsigned int)
	return wfp;
}

wavefileplayer* wavefileplayer_create_from_path(char* path, int loop)
{
	FILE* fp;
	fp = fopen(path, "r");
	wavefileplayer* wfp = wavefileplayer_create_from_file(fp, loop);
	fclose(fp);
	return wfp;
}

void wavefileplayer_getSamples(wavefileplayer* wfp, int targetSize,
		char* target)
{
	int i;

	for (i = 0; i < targetSize; i++)
	{
		if (wfp->offset >= wfp->arraysize)
		{
			if (wfp->loop)
			{
				wfp->offset = 0;
				target[i] = wfp->data[0];
			}
			else
			{
				target[i] = 0;
			}
		}
		else
		{
			target[i] = wfp->data[wfp->offset];
		}
		wfp->offset++;
	}
}

void wavefileplayer_destroy(wavefileplayer* player)
{
	free(player->data);
	free(player);
}

//TODO put into softwarethread

