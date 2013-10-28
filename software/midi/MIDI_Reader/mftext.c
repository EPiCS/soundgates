/*
 * mftext
 * 
 * Convert a MIDI file to verbose text.
 */

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <errno.h>
#include "midifile.h"

void prtime();
void initfuncs();

int SECONDS;      /* global that tells whether to display seconds or ticks */
int division;        /* from the file header */

int verbose = 0;  /* when 1 it prints the messages in the original mftext
		     program, when 0 it does not print them */

long tempo = 500000; /* the default tempo is 120 beats/minute  */

int counter = 0;
int isEnd = 0;

int midiBytes[742] = {77, 84, 104, 100, 0, 0, 0, 6, 0, 1, 0, 2, 1, 224, 77, 
84, 114, 107, 0, 0, 0, 35, 0, 255, 81, 3, 8, 249, 203, 0, 
255, 3, 12, 101, 69, 77, 80, 73, 82, 69, 46, 83, 66, 49, 54, 
0, 255, 88, 4, 4, 2, 8, 249, 0, 255, 47, 0, 77, 84, 114, 
107, 0, 0, 2, 165, 0, 255, 3, 9, 196, 238, 240, 238, 230, 234, 
224, 32, 54, 0, 183, 100, 0, 0, 101, 0, 0, 6, 12, 0, 231, 
0, 64, 0, 183, 101, 0, 0, 100, 1, 0, 6, 64, 0, 38, 0, 
0, 101, 127, 0, 100, 127, 0, 101, 0, 0, 100, 2, 0, 6, 64, 
0, 101, 127, 0, 100, 127, 0, 199, 57, 0, 183, 0, 0, 0, 7, 
112, 0, 10, 64, 0, 93, 0, 0, 91, 0, 0, 92, 0, 0, 95, 
0, 0, 151, 55, 127, 129, 112, 135, 55, 80, 129, 112, 151, 55, 127, 
130, 44, 135, 55, 80, 129, 52, 151, 55, 127, 130, 44, 135, 55, 80, 
129, 52, 151, 51, 127, 129, 112, 135, 51, 80, 120, 151, 58, 127, 60, 
135, 58, 80, 60, 151, 55, 111, 130, 104, 135, 55, 80, 120, 151, 51, 
127, 129, 112, 135, 51, 80, 120, 151, 58, 111, 60, 135, 58, 80, 60, 
151, 55, 111, 132, 88, 135, 55, 80, 130, 104, 151, 62, 127, 130, 44, 
135, 62, 80, 129, 52, 151, 62, 127, 130, 44, 135, 62, 80, 129, 52, 
151, 62, 111, 130, 44, 135, 62, 80, 129, 52, 151, 63, 127, 129, 112, 
135, 63, 80, 120, 151, 58, 127, 60, 135, 58, 80, 60, 151, 54, 111, 
130, 104, 135, 54, 80, 120, 151, 51, 127, 129, 52, 135, 51, 80, 129, 
52, 151, 58, 127, 60, 135, 58, 80, 60, 151, 55, 111, 131, 36, 135, 
55, 80, 132, 28, 151, 67, 127, 130, 104, 135, 67, 80, 120, 151, 55, 
111, 129, 112, 135, 55, 80, 120, 151, 55, 127, 120, 135, 55, 80, 0, 
151, 67, 111, 130, 44, 135, 67, 80, 129, 52, 151, 66, 127, 129, 112, 
135, 66, 80, 120, 151, 65, 111, 80, 135, 65, 80, 40, 151, 64, 111, 
120, 135, 64, 80, 0, 151, 63, 111, 60, 135, 63, 80, 60, 151, 64, 
111, 120, 135, 64, 80, 130, 104, 151, 56, 95, 120, 135, 56, 80, 120, 
151, 61, 95, 130, 44, 135, 61, 80, 129, 52, 151, 60, 95, 129, 112, 
135, 60, 80, 120, 151, 59, 95, 60, 135, 59, 80, 60, 151, 58, 95, 
120, 135, 58, 80, 0, 151, 57, 95, 60, 135, 57, 80, 60, 151, 58, 
95, 120, 135, 58, 80, 130, 104, 151, 51, 95, 80, 135, 51, 80, 129, 
32, 151, 54, 95, 130, 44, 135, 54, 80, 129, 52, 151, 51, 95, 130, 
44, 135, 51, 80, 60, 151, 54, 95, 60, 135, 54, 80, 120, 151, 58, 
95, 130, 44, 135, 58, 80, 120, 151, 55, 95, 129, 112, 135, 55, 80, 
120, 151, 58, 95, 60, 135, 58, 80, 60, 151, 62, 95, 132, 88, 135, 
62, 80, 130, 104, 151, 67, 127, 130, 104, 135, 67, 80, 120, 151, 55, 
111, 129, 112, 135, 55, 80, 120, 151, 55, 127, 120, 135, 55, 80, 0, 
151, 67, 111, 130, 44, 135, 67, 80, 129, 52, 151, 66, 127, 129, 112, 
135, 66, 80, 120, 151, 65, 111, 80, 135, 65, 80, 40, 151, 64, 111, 
120, 135, 64, 80, 0, 151, 63, 111, 60, 135, 63, 80, 60, 151, 64, 
111, 120, 135, 64, 80, 130, 104, 151, 56, 95, 120, 135, 56, 80, 120, 
151, 61, 95, 130, 44, 135, 61, 80, 129, 52, 151, 60, 95, 129, 112, 
135, 60, 80, 120, 151, 59, 95, 60, 135, 59, 80, 60, 151, 58, 95, 
120, 135, 58, 80, 0, 151, 57, 95, 60, 135, 57, 80, 60, 151, 58, 
95, 120, 135, 58, 80, 130, 104, 151, 51, 95, 80, 135, 51, 80, 129, 
32, 151, 54, 95, 130, 44, 135, 54, 80, 129, 52, 151, 51, 95, 130, 
44, 135, 51, 80, 60, 151, 58, 95, 60, 135, 58, 80, 60, 151, 55, 
95, 130, 44, 135, 55, 80, 129, 52, 151, 51, 95, 129, 112, 135, 51, 
80, 120, 151, 58, 95, 60, 135, 58, 80, 60, 151, 55, 95, 132, 88, 
135, 55, 80, 0, 255, 47, 0};

int filegetc()
{
    int x;
    x = midiBytes[counter];
    counter=counter+1;
    if(counter==sizeof(midiBytes))
        isEnd=1;
	return(x);
}

int main(argc,argv)
char **argv;
{
	char ch;
	int i, lowest, argcount;

	n=0;
	SECONDS = 0;
	argcount = 1;

    SECONDS = 1;

	initfuncs();

	for(i=0; i<100; i++) davypitch[i]=-1;
	for(i=0; i<100000; i++) davynote[i].done=0;

	Mf_getc = filegetc;

	/* This next function does all the work - Davy */
	midifile();         
 
	while(1) {
	  lowest=1000000;
	  for(i=0; i<n; i++) {
	    if(davynote[i].done==1) continue;
	    if(davynote[i].ontime<lowest) lowest=davynote[i].ontime;
	  }
	  for(i=0; i<n; i++) {
	    if(davynote[i].ontime==lowest && davynote[i].done==0) {
          printf("TEST Note %6d %6d %2d\n", davynote[i].ontime, davynote[i].offtime, davynote[i].pitch);
//	      fprintf(out_file,"notes[%1d].ontime=%1d;\n notes[%1d].offtime=%1d;\n notes[%1d].note=%1d;\n", i, davynote[i].ontime, i, davynote[i].offtime, i, davynote[i].pitch);

	      davynote[i].done=1;
	    }
	  }
	  if(lowest==1000000) break;
	}



    system("PAUSE");
	exit(0);
}

void error(s)
char *s;
{
	fprintf(stderr,"Error: %s\n",s);
}

void txt_header(format,ntrks,ldivision)
{
        division = ldivision;
	if (verbose) {
	    printf("Header format=%d ntrks=%d division=%d\n",format,ntrks,division);
	}
}

void txt_trackstart()
{
	if (verbose) printf("Track start\n");
}

void txt_trackend()
{
	if (verbose) printf("Track end\n");
}

/*
txt_noteon(chan,pitch,vol)
{
	prtime();
	printf("Note on, chan=%d pitch=%d vol=%d\n",chan+1,pitch,vol);
}

txt_noteoff(chan,pitch,vol)
{
	prtime();
	printf("Note off, chan=%d pitch=%d vol=%d\n",chan+1,pitch,vol);
}
*/

void txt_noteon(chan,pitch,vol)
{
        int newtime = (int) (1000 * mf_ticks2sec(Mf_currtime,division,tempo));
        /* print time in milliseconds */

        if(davypitch[pitch]!=-1) {
	  davynote[n].ontime=davypitch[pitch];
	  davynote[n].offtime=newtime;
	  davynote[n].pitch=pitch;
	  n++;
	  /*printf("Note %6d %6d %2d\n", davypitch[pitch], (int) (1000 * mf_ticks2sec(Mf_currtime,division,tempo)), pitch);  */
	}
	davypitch[pitch]=newtime;
	/*
        printf("Note-on ");
	printf("%d ",(int) (1000 * mf_ticks2sec(Mf_currtime,division,tempo)));  
	printf("%d\n",pitch); */

}

void txt_noteoff(chan,pitch,vol)
{
        int newtime = (int) (1000 * mf_ticks2sec(Mf_currtime, division,tempo));
        /*if(davypitch[pitch]==-1) printf("Error: Pitch %d is being turned off when it's not on\n", pitch);*/

        if(davypitch[pitch]!=-1 && davypitch[pitch]!=newtime) {
	  davynote[n].ontime=davypitch[pitch];
	  davynote[n].offtime=newtime;
	  davynote[n].pitch=pitch;
	  n++;
	  /*	  printf("Note %6d %6d %2d\n", davypitch[pitch], (int) (1000 * mf_ticks2sec(Mf_currtime,division,tempo)), pitch);  */
	  davypitch[pitch]=-1;
	}

  /*        printf("Note-off ");
	printf("%d ", (int) (1000 * mf_ticks2sec(Mf_currtime,division,tempo)));  
	printf("%d\n",pitch); */

}

void txt_pressure(chan,pitch,press)
{
	prtime();
	printf("Pressure, chan=%d pitch=%d press=%d\n",chan+1,pitch,press);
}

void txt_parameter(chan,control,value)
{
	prtime();
	printf("Parameter, chan=%d c1=%d c2=%d\n",chan+1,control,value);
}

void txt_pitchbend(chan,msb,lsb)
{
	prtime();
	printf("Pitchbend, chan=%d msb=%d lsb=%d\n",chan+1,msb,lsb);
}

void txt_program(chan,program)
{
	prtime();
	printf("Program, chan=%d program=%d\n",chan+1,program);
}

void txt_chanpressure(chan,press)
{
	prtime();
	printf("Channel pressure, chan=%d pressure=%d\n",chan+1,press);
}

void txt_sysex(leng,mess)
char *mess;
{
	prtime();
	printf("Sysex, leng=%d\n",leng);
}

void txt_metamisc(type,leng,mess)
char *mess;
{
	prtime();
	printf("Meta event, unrecognized, type=0x%02x leng=%d\n",type,leng);
}

void txt_metaspecial(type,leng,mess)
char *mess;
{
	prtime();
	printf("Meta event, sequencer-specific, type=0x%02x leng=%d\n",type,leng);
}

void txt_metatext(type,leng,mess)
char *mess;
{
	static char *ttype[] = {
		NULL,
		"Text Event",		/* type=0x01 */
		"Copyright Notice",	/* type=0x02 */
		"Sequence/Track Name",
		"Instrument Name",	/* ...       */
		"Lyric",
		"Marker",
		"Cue Point",		/* type=0x07 */
		"Unrecognized"
	};
	int unrecognized = (sizeof(ttype)/sizeof(char *)) - 1;
	register int n, c;
	register char *p = mess;

	if ( type < 1 || type > unrecognized )
		type = unrecognized;
	prtime();
	printf("Meta Text, type=0x%02x (%s)  leng=%d\n",type,ttype[type],leng);
	printf("     Text = <");
	for ( n=0; n<leng; n++ ) {
		c = *p++;
		printf( (isprint(c)||isspace(c)) ? "%c" : "\\0x%02x" , c);
	}
	printf(">\n");
}

void txt_metaseq(num)
{
	prtime();
	printf("Meta event, sequence number = %d\n",num);
}

void txt_metaeot()
{
	prtime();
	if (verbose) printf("Meta event, end of track\n");
}

void txt_keysig(sf,mi)
{
	prtime();
	if (verbose) printf("Key signature, sharp/flats=%d  minor=%d\n",sf,mi);
}

void txt_tempo(ltempo)
long ltempo;
{
	tempo = ltempo;
	prtime();
	if (verbose) printf("Tempo, microseconds-per-MIDI-quarter-note=%ld\n",tempo);
}

void txt_timesig(nn,dd,cc,bb)
{
	int denom = 1;
	while ( dd-- > 0 )
		denom *= 2;
	prtime();
	if (verbose) printf("Time signature=%d/%d  MIDI-clocks/click=%d  32nd-notes/24-MIDI-clocks=%d\n",
		nn,denom,cc,bb);
}

void txt_smpte(hr,mn,se,fr,ff)
{
	prtime();
	printf("SMPTE, hour=%d minute=%d second=%d frame=%d fract-frame=%d\n",
		hr,mn,se,fr,ff);
}

void txt_arbitrary(leng,mess)
char *mess;
{
	prtime();
	printf("Arbitrary bytes, leng=%d\n",leng);
}

void prtime()
{
    if (verbose) {
	if(SECONDS) {
	    printf("Time=%f   ",mf_ticks2sec(Mf_currtime,division,tempo));
	} else {
	    printf("Time=%ld  ",Mf_currtime);
	}
    }
}

int txt_isEnd(){
     return isEnd;
}

void initfuncs()
{
	Mf_error = error;
	Mf_header =  txt_header;
	Mf_trackstart =  txt_trackstart;
	Mf_trackend =  txt_trackend;
	Mf_noteon =  txt_noteon;
	Mf_noteoff =  txt_noteoff;
	Mf_pressure =  txt_pressure;
	Mf_parameter =  txt_parameter;
	Mf_pitchbend =  txt_pitchbend;
	Mf_program =  txt_program;
	Mf_chanpressure =  txt_chanpressure;
	Mf_sysex =  txt_sysex;
	Mf_metamisc =  txt_metamisc;
	Mf_seqnum =  txt_metaseq;
	Mf_eot =  txt_metaeot;
	Mf_timesig =  txt_timesig;
	Mf_smpte =  txt_smpte;
	Mf_tempo =  txt_tempo;
	Mf_keysig =  txt_keysig;
	Mf_seqspecific =  txt_metaspecial;
	Mf_text =  txt_metatext;
	Mf_arbitrary =  txt_arbitrary;
	
	Mf_isEnd = txt_isEnd;
}
