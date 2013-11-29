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

	boost::regex nodexpr("([0-9]+) ([a-z]+)/([a-z]+)(\\(([0-9]+)\\))?(/('.*',?)+)? *");

	boost::regex edgeexpr("([0-9]+) ([0-9]+) '([0-9]+),([0-9]+)'  *");

	boost::smatch match;

	while(sgfile){

		getline(sgfile, line);

		// match node
		if (boost::regex_match(line, match, nodexpr)){

			int uid = boost::lexical_cast<int>(match[1]);
			std::string type = match[2];
			std::string impltype = match[3];
			std::string params = match[8];

#ifdef DEBUG
			std::cout << "1: " << match[1] << std::endl;
			std::cout << "2: " << match[2] << std::endl;
			std::cout << "3: " << match[3] << std::endl;
			std::cout << "4: " << match[4] << std::endl;
			std::cout << "5: " << match[5] << std::endl;
			std::cout << "6: " << match[6] << std::endl;
			std::cout << "7: " << match[7] << std::endl;
			std::cout << "8: " << match[8] << std::endl;
			std::cout << "----------------------------" << std::endl;
#endif
			if(!impltype.compare(SoundComponents::ImplTypeNames[SoundComponents::HW])){

				int slot = boost::lexical_cast<int>(match[5]);

				patch->createSoundComponent(uid, type, slot, params);

			}else if(!impltype.compare(SoundComponents::ImplTypeNames[SoundComponents::SW])){

				patch->createSoundComponent(uid, type, params);
			}else{

				std::cout << "Error: unknown implementation type" << std::endl;
			}

		}

		// match edge
		if (boost::regex_match(line, match, edgeexpr)){

			//std::cout << "Source: " << match[1] << " Dest: " << match[2] << " SourcePort: " << match[3] << " DestPort: " << match[4] << std::endl;
		}
	}
}



