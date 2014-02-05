/*
 * HWThread.h
 *
 *  Created on: Jan 16, 2014
 *      Author: lukas
 */

#ifndef HWTHREAD_H_
#define HWTHREAD_H_

#include <stdint.h>

#define SOUNDGATES_FIXED_PT_SCALE (1 << 28) // 2^28
#define SOUNDGATES_FIX_PT_SCALE(x) x << 28

template <uint32_t nDataSrc = 1, uint32_t nOptargs = 1>
class HWTParameters {
public:

//    struct ParamtStruct_t {
//         void*    src_addr[nDataSrc] __attribute__ ((aligned (nDataSrc * 4)));
//         uint32_t src_len            __attribute__ ((aligned (4)));
//         void*    dest_addr          __attribute__ ((aligned (4)));
//         void*    opt_arg_addr       __attribute__ ((aligned (4)));
//         uint32_t opt_args[nOptargs] __attribute__ ((aligned (nOptargs * 4)));
//     };

    struct ParamtStruct_t {
         void*    src_addr[nDataSrc];
         uint32_t src_len           ;
         void*    dest_addr         ;
         void*    opt_arg_addr      ;
         uint32_t opt_args[nOptargs];
     };


    HWTParameters();
    virtual ~HWTParameters();
};

#endif /* HWTHREAD_H_ */
