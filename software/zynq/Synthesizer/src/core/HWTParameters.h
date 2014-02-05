/*
 * HWThread.h
 *
 *  Created on: Jan 16, 2014
 *      Author: lukas
 */

#ifndef HWTHREAD_H_
#define HWTHREAD_H_

#include <stdint.h>

#define SOUNDGATES_FIXED_PT_SCALE (float)(1 << 28) // 2^28
#define SOUNDGATES_FIX_PT_SCALE(x) x << 28

template <uint32_t nArgs>
class HWTParameters {
public:

    uint32_t args[nArgs];

    HWTParameters();
    virtual ~HWTParameters();
};

#endif /* HWTHREAD_H_ */
