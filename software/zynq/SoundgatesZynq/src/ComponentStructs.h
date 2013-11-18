#ifndef COMPONENTSTRUCTS_H
#define COMPONENTSTRUCTS_H

#define ID_SIN   1
#define ID_SAW   2
#define ID_TRI   3
#define ID_SQR   4
#define ID_RMP   5
#define ID_ADSR  6
#define ID_PRB   7 // white noise
#define ID_MIX   8
#define ID_BIAS  9

typedef struct {
    void* src_addr;
    int src_len;
    void* dest_addr;
    void* opt_arg_addr;
} sSoundComponentHeader;

typedef struct {
	int phase_offset;
	int phase_increment;
} sNcoComponentHeader;

typedef struct{
    char                  *comp_osc_name;
    void    		      *comp_value_pointer;
    int                    comp_id;
    struct sOSCComponent  *next;
} sOSCComponent;

//typedef struct {
//    sBase base;
//    int *offset;
//    int *increment;
//} sHeaderSine;

#endif
