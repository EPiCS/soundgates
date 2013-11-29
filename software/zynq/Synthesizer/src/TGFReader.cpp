/*
 * TGFReader.cpp
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */

#include "TGFReader.h"


TGFReader::TGFReader(){ }

TGFReader::~TGFReader(){ }

void TGFReader::read(Patch* patch, std::string filename){

	std::string line;

	std::vector<std::string> linetokens;

	std::ifstream sgfile(filename.c_str());

	if(!sgfile.good()){

		std::cout << "ERROR: input file does not exist" << std::endl;
		return;

	}

	boost::regex nodexpr("([0-9]+) ([a-z]+)/([a-z]+(\([0-9]+\))?)(/('.*',?)+)?");

	boost::regex edgeexpr("([0-9]+) ([0-9]+) '([0-9]+),([0-9]+)'");

	boost::smatch match;

	while(sgfile){

		getline(sgfile, line);

		// match node
		if (boost::regex_match(line, match, nodexpr)){

			std::cout << "Node: " << match[1] << " Type: " << match[2] << " ImplType: " << match[3] << std::endl;
		}

		// match edge
		if (boost::regex_match(line, match, edgeexpr)){

			std::cout << "Source: " << match[1] << " Dest: " << match[2] << " SourcePort: " << match[3] << " DestPort: " << match[4] << std::endl;
		}
	}
}



