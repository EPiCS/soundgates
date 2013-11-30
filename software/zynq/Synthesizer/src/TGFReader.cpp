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
		BOOST_LOG_TRIVIAL(error) << "input file does not exist";
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
			std::string params = match[7];
			std::vector<std::string> paramtokens;
			boost::split(paramtokens, params, boost::is_any_of(","));

			BOOST_LOG_TRIVIAL(debug) << "TGF match " << "1: " << match[1];
			BOOST_LOG_TRIVIAL(debug) << "TGF match " << "2: " << match[2];
			BOOST_LOG_TRIVIAL(debug) << "TGF match " << "3: " << match[3];
			BOOST_LOG_TRIVIAL(debug) << "TGF match " <<  "4: " << match[4];
			BOOST_LOG_TRIVIAL(debug) << "TGF match " <<  "5: " << match[5];
			BOOST_LOG_TRIVIAL(debug) << "TGF match " <<  "6: " << match[6];
			BOOST_LOG_TRIVIAL(debug) << "TGF match " <<  "7: " << match[7];
			BOOST_LOG_TRIVIAL(debug) << "TGF match " <<  "8: " << match[8];

			if(!impltype.compare(SoundComponents::ImplTypeNames[SoundComponents::HW])){

				int slot = boost::lexical_cast<int>(match[5]);

				patch->createSoundComponent(uid, type, slot, paramtokens);

			}else if(!impltype.compare(SoundComponents::ImplTypeNames[SoundComponents::SW])){

				patch->createSoundComponent(uid, type, paramtokens);

			}else{

				BOOST_LOG_TRIVIAL(error) << "unknown implementation type";
			}
		}

		// match edge
		if (boost::regex_match(line, match, edgeexpr)){

			//std::cout << "Source: " << match[1] << " Dest: " << match[2] << " SourcePort: " << match[3] << " DestPort: " << match[4] << std::endl;
		}
	}
}



