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

#include <boost/regex.hpp>
#include <boost/lexical_cast.hpp>
#include <boost/algorithm/string.hpp>
#include <boost/algorithm/string/split.hpp>

#include <boost/log/trivial.hpp>

#include "Patch.h"
#include "Synthesizer.h"

class TGFReader {

public:

	TGFReader();
	~TGFReader();

	void read(Patch*, std::string);
};


#endif /* TGFREADER_H_ */
