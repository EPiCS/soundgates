/*
 * SynthesizerFileReader.h
 *
 *  Created on: Nov 30, 2013
 *      Author: lukas
 */

#ifndef SYNTHESIZERFILEREADER_H_
#define SYNTHESIZERFILEREADER_H_

#include <string>
#include "Patch.h"


class SynthesizerFileReader {

public:
	SynthesizerFileReader();
	virtual ~SynthesizerFileReader();


	virtual void read(Patch*, std::string) = 0;

};

#endif /* SYNTHESIZERFILEREADER_H_ */
