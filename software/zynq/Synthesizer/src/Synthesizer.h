/*
 * Synthesizer.h
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */

#ifndef SYNTHESIZER_H_
#define SYNTHESIZER_H_


namespace Synthesizer{

}


namespace SoundComponents{

	enum ImplType { HW, SW, SIZE_OF_IMPLTYPES};

	static const char* ImplTypeNames[SIZE_OF_IMPLTYPES] = {"hw", "sw"};

}

#endif /* SYNTHESIZER_H_ */
