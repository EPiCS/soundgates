/*
 * fir_coeffs.hpp
 *
 *  Created on: Feb 3, 2014
 *      Author: lukas
 */

#ifndef FIR_COEFF_HPP_
#define FIR_COEFF_HPP_

#include <stdint.h>

#define N_FIR_COEFF          29      // Number of filter coefficients
#define N_CUTOFF_VECTOR_SIZE 8001

extern int32_t coeff_hp[][29];
extern int32_t coeff_lp[][29];

#endif /* FIR_COEFFS_HPP_ */
