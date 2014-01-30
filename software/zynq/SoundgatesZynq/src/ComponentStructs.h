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
#define ID_BEAT 10

typedef struct OSCComponent_struct sOSCComponent;
typedef struct OSCComponent_struct* sOSCComponentPtr;

typedef struct sNCO_struct sSoundcomponent_NCO;
typedef struct sNCO_struct* sSoundcomponent_NCOPtr;

typedef struct {
    void* src_addr;
    int src_len;
    void* dest_addr;
    void* opt_arg_addr;
} sSoundComponentHeader;

typedef struct {
	int phase_offset;
	int phase_increment;
} sWaveComponentHeader; // sinus, sawtooth, triangle

typedef struct {
	int phase_offset;
	int phase_increment;
	int duty_on;
	int duty_off;
} sSquareComponentHeader;

typedef struct {
    int bang;
    int stop;
    int atck_dura;
    int dcay_dura;
    int rlse_dura;
    int strt_amp;
    int atck_amp;
    int sust_amp;
    int rlse_amp;
} sADSRComponentHeader;

typedef struct {
    int incr;
    int decr;
    int start_amp;
    int upper_amp;
    int end_amp;
} sRAMPComponentHeader;

typedef struct {    
	int master_offset;
	int master_increment;
	int slave_offset;
	int slave_increment;
} sNCOSyncComponentHeader;

typedef struct {
    int coefficient_count;
    int coefficients;
} sFIRComponentHeader;

typedef struct {
    int coefficient_count;
    int coefficients;
    int feedback_coefficients;
} sIIRComponentHeader;

typedef struct {
    int control_sound_in;
} sArithmeticComponentHeader;

typedef struct {
    int percentage;
} sAmplifierComponentHeader;

typedef struct {
    int trigger;
} sSampleHoldComponentHeader;

typedef struct {
    int sound_in_slave;
} sPWMComponentHeader;

struct OSCComponent_struct{
    char                  *comp_osc_name;
    void    		      *comp_value_pointer;
    int                    comp_id;
    sOSCComponentPtr next;
};

//typedef struct {
//    sBase base;
//    int *offset;
//    int *increment;
//} sHeaderSine;

#endif
