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

	enum PredefinedComponents { INPUT, SIZE_OF_PREDEF_COMPONENTS };

	static const char* ImplTypeNames[SIZE_OF_IMPLTYPES] = {"hw", "sw"};

	static const char* PredefinedComponentsNames[SIZE_OF_PREDEF_COMPONENTS] = { "input" };
}

#endif /* SYNTHESIZER_H_ */
