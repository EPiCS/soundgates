/*
 * CommonComponent.h
 *
 *  Created on: Nov 26, 2013
 *      Author: gwue
 */

#ifndef COMMONCOMPONENT_H_
#define COMMONCOMPONENT_H_

#define MBOX_CTRL_START 0
#define MBOX_CTRL_STOP  1
#define MBOX_CTRL_SIZE  1
#define MBOX_CTRL_COUNT 2

#if _TARGET_ZYNQ==1

#include <reconos.h>
#include <mbox.h>
#endif

typedef enum {
	NCO, GENERIC
} ComponentTypes;

typedef enum SndHWCompnentState_enum eSndHWComponent_State;
enum SndHWCompnentState_enum {
	HWT_INIT, HWT_RUNNING, HWT_PAUSED, HWT_TERMINATED
};

typedef struct SndCommonComponentHeader_Struct sSndCommonComponentHeader;
typedef struct SndCommonComponentHeader_Struct* sSndCommonComponentHeaderPtr;

typedef struct ReconosCommon_Struct sReconosCommon;
typedef struct ReconosCommon_Struct* sReconosCommonPtr;

struct SndCommonComponentHeader_Struct {
	void* src_addr;
	int src_len;
	void* dest_addr;
	void* opt_arg_addr;
};

struct ReconosCommon_Struct {
	int slot;
	struct mbox ctrl[MBOX_CTRL_COUNT];
	struct reconos_resource resource;
	struct reconos_hwt thread;
};



#endif /* COMMONCOMPONENT_H_ */
