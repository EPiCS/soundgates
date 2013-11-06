#include "reconos.h"
#include "mbox.h"
#include "notes.h"

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
#include <math.h>

// hw thread
struct reconos_hwt hwt;
struct reconos_resource res[2];

// message boxes
struct mbox  mb_writefrequency;
struct mbox  mb_debug;

void fillArray()
{
	notes[0].ontime=0;
	notes[0].offtime=294;
	notes[0].note=55;
	notes[1].ontime=588;
	notes[1].offtime=955;
	notes[1].note=55;
	notes[2].ontime=1176;
	notes[2].offtime=1544;
	notes[2].note=55;
	notes[3].ontime=1764;
	notes[3].offtime=2058;
	notes[3].note=51;
	notes[4].ontime=2205;
	notes[4].offtime=2279;
	notes[4].note=58;
	notes[5].ontime=2352;
	notes[5].offtime=2794;
	notes[5].note=55;
	notes[6].ontime=2941;
	notes[6].offtime=3235;
	notes[6].note=51;
	notes[7].ontime=3382;
	notes[7].offtime=3455;
	notes[7].note=58;
	notes[8].ontime=3529;
	notes[8].offtime=4264;
	notes[8].note=55;
	notes[9].ontime=4705;
	notes[9].offtime=5073;
	notes[9].note=62;
	notes[10].ontime=5294;
	notes[10].offtime=5661;
	notes[10].note=62;
	notes[11].ontime=5882;
	notes[11].offtime=6249;
	notes[11].note=62;
	notes[12].ontime=6470;
	notes[12].offtime=6764;
	notes[12].note=63;
	notes[13].ontime=6911;
	notes[13].offtime=6985;
	notes[13].note=58;
	notes[14].ontime=7058;
	notes[14].offtime=7499;
	notes[14].note=54;
	notes[15].ontime=7647;
	notes[15].offtime=7867;
	notes[15].note=51;
	notes[16].ontime=8088;
	notes[16].offtime=8161;
	notes[16].note=58;
	notes[17].ontime=8235;
	notes[17].offtime=8749;
	notes[17].note=55;
	notes[18].ontime=9411;
	notes[18].offtime=9852;
	notes[18].note=67;
	notes[19].ontime=9999;
	notes[19].offtime=10294;
	notes[19].note=55;
	notes[20].ontime=10441;
	notes[20].offtime=10588;
	notes[20].note=55;
	notes[21].ontime=10588;
	notes[21].offtime=10955;
	notes[21].note=67;
	notes[22].ontime=11176;
	notes[22].offtime=11470;
	notes[22].note=66;
	notes[23].ontime=11617;
	notes[23].offtime=11715;
	notes[23].note=65;
	notes[24].ontime=11764;
	notes[24].offtime=11911;
	notes[24].note=64;
	notes[25].ontime=11911;
	notes[25].offtime=11985;
	notes[25].note=63;
	notes[26].ontime=12058;
	notes[26].offtime=12205;
	notes[26].note=64;
	notes[27].ontime=12647;
	notes[27].offtime=12794;
	notes[27].note=56;
	notes[28].ontime=12941;
	notes[28].offtime=13308;
	notes[28].note=61;
	notes[29].ontime=13529;
	notes[29].offtime=13823;
	notes[29].note=60;
	notes[30].ontime=13970;
	notes[30].offtime=14044;
	notes[30].note=59;
	notes[31].ontime=14117;
	notes[31].offtime=14264;
	notes[31].note=58;
	notes[32].ontime=14264;
	notes[32].offtime=14338;
	notes[32].note=57;
	notes[33].ontime=14411;
	notes[33].offtime=14558;
	notes[33].note=58;
	notes[34].ontime=14999;
	notes[34].offtime=15098;
	notes[34].note=51;
	notes[35].ontime=15294;
	notes[35].offtime=15661;
	notes[35].note=54;
	notes[36].ontime=15882;
	notes[36].offtime=16249;
	notes[36].note=51;
	notes[37].ontime=16323;
	notes[37].offtime=16397;
	notes[37].note=54;
	notes[38].ontime=16544;
	notes[38].offtime=16911;
	notes[38].note=58;
	notes[39].ontime=17058;
	notes[39].offtime=17352;
	notes[39].note=55;
	notes[40].ontime=17499;
	notes[40].offtime=17573;
	notes[40].note=58;
	notes[41].ontime=17647;
	notes[41].offtime=18382;
	notes[41].note=62;
	notes[42].ontime=18823;
	notes[42].offtime=19264;
	notes[42].note=67;
	notes[43].ontime=19411;
	notes[43].offtime=19705;
	notes[43].note=55;
	notes[44].ontime=19852;
	notes[44].offtime=19999;
	notes[44].note=55;
	notes[45].ontime=19999;
	notes[45].offtime=20367;
	notes[45].note=67;
	notes[46].ontime=20588;
	notes[46].offtime=20882;
	notes[46].note=66;
	notes[47].ontime=21029;
	notes[47].offtime=21127;
	notes[47].note=65;
	notes[48].ontime=21176;
	notes[48].offtime=21323;
	notes[48].note=64;
	notes[49].ontime=21323;
	notes[49].offtime=21397;
	notes[49].note=63;
	notes[50].ontime=21470;
	notes[50].offtime=21617;
	notes[50].note=64;
	notes[51].ontime=22058;
	notes[51].offtime=22205;
	notes[51].note=56;
	notes[52].ontime=22352;
	notes[52].offtime=22720;
	notes[52].note=61;
	notes[53].ontime=22941;
	notes[53].offtime=23235;
	notes[53].note=60;
	notes[54].ontime=23382;
	notes[54].offtime=23455;
	notes[54].note=59;
	notes[55].ontime=23529;
	notes[55].offtime=23676;
	notes[55].note=58;
	notes[56].ontime=23676;
	notes[56].offtime=23749;
	notes[56].note=57;
	notes[57].ontime=23823;
	notes[57].offtime=23970;
	notes[57].note=58;
	notes[58].ontime=24411;
	notes[58].offtime=24509;
	notes[58].note=51;
	notes[59].ontime=24705;
	notes[59].offtime=25073;
	notes[59].note=54;
	notes[60].ontime=25294;
	notes[60].offtime=25661;
	notes[60].note=51;
	notes[61].ontime=25735;
	notes[61].offtime=25808;
	notes[61].note=58;
	notes[62].ontime=25882;
	notes[62].offtime=26249;
	notes[62].note=55;
	notes[63].ontime=26470;
	notes[63].offtime=26764;
	notes[63].note=51;
	notes[64].ontime=26911;
	notes[64].offtime=26985;
	notes[64].note=58;
	notes[65].ontime=27058;
	notes[65].offtime=27794;
	notes[65].note=55;
}

void initializeReconOS()
{
	uint32_t message = 0;
	uint32_t answer = 0;
	mbox_init(&mb_writefrequency,5);
	mbox_init(&mb_debug,5);
	
	reconos_cache_flush();
	reconos_init_autodetect();
	reconos_cache_flush();
	
	printf("Creating mboxes \n");
	res[0].type = RECONOS_TYPE_MBOX;
	res[0].ptr  = &mb_writefrequency;
	res[1].type = RECONOS_TYPE_MBOX;
	res[1].ptr  = &mb_debug;
	
	//res[0].type = RECONOS_TYPE_SEM;
	//res[0].ptr  = &sem_a;
	
	//sem_init(&sem_a,0,0);
	//sem_init(&sem_b,0,0);
	
	reconos_hwt_setresources(&hwt,res,2);
	reconos_hwt_setinitdata(&hwt, (void *)message);
	reconos_hwt_create(&hwt,0,NULL);
}

float getFreqFromNote(float x)
{ 
	float f1 = pow(2,(x/12));
	float f2 = 8.1758*f1;
	return f2;
}

unsigned long getAnswer ()
{
 	return  mbox_get( &mb_debug );
}

void setFrequence(uint32_t frequence)
{
	printf("Entering Set frequence method \n");
	mbox_put( &mb_writefrequency, frequence );
	uint32_t answer = getAnswer();
	printf("%i", answer);
	printf("Exiting Set frequence method \n");
}

int main(int argc, char ** argv)
{
	printf("Initialize midi \n");
	fillArray();
	printf("Initialize ReconOS \n");
	initializeReconOS();
	
  	int i;
    int size = sizeof(notes) / sizeof(notes[0]);
    int lastOfftime = 0;
    int pauseDuration = 0;
    int playDuration = 0;
	printf("Entering while \n");
    while(1){
    	for(lastOfftime = 0, i = 0; i<size; i++)  {
			printf("Playing a note \n");
		    struct midiNote currentMidiNote = notes[i];
			pauseDuration = currentMidiNote.ontime - lastOfftime;
		    playDuration = currentMidiNote.offtime - currentMidiNote.ontime;

		    setFrequence(0);
		    usleep(1000*pauseDuration);
			setFrequence(getFreqFromNote(currentMidiNote.note-16));
		    usleep(1000*pauseDuration);

		    lastOfftime=currentMidiNote.offtime;
      	}
    }
	
	return 0;
}

