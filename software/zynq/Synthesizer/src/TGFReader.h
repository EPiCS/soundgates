/*
 * TGFReader.h
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */

#ifndef TGFREADER_H_
#define TGFREADER_H_

#include <fstream>
#include <iostream>
#include <vector>
#include <string>
#include <exception>

#include <boost/regex.hpp>
#include <boost/lexical_cast.hpp>
#include <boost/algorithm/string.hpp>
#include <boost/algorithm/string/erase.hpp>
#include <boost/algorithm/string/split.hpp>



#include "Patch.h"
#include "SynthesizerFileReader.h"

#include "core/Synthesizer.h"


using namespace std;

class TGFReader : public SynthesizerFileReader {

private:

	void normalize(vector<string>&);

public:

	TGFReader();
	virtual ~TGFReader();

	void read(Patch*, string);
};


#endif /* TGFREADER_H_ */
