/*
 * TGFReader.cpp
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */

#include "TGFReader.h"


TGFReader::TGFReader(){ }

TGFReader::~TGFReader(){ }


void TGFReader::normalize(vector<string>& params){

	for(vector<string>::iterator iter = params.begin(); iter != params.end(); ++iter ){

		boost::algorithm::erase_first((*iter), "'");
		boost::algorithm::erase_last((*iter),  "'");

		BOOST_LOG_TRIVIAL(debug) << "Normalizing token " << *iter;
	}
}

void TGFReader::read(Patch* patch, string filename){

	string line;

	vector<string> linetokens;

	ifstream sgfile(filename.c_str());

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
			string type = match[2];
			string impltype = match[3];
			string params = match[7];

			vector<string> paramtokens;
			boost::split(paramtokens, params, boost::is_any_of(","));

			normalize(paramtokens);


			BOOST_LOG_TRIVIAL(debug) << "TGF match node " << "1: "  << match[1];
			BOOST_LOG_TRIVIAL(debug) << "TGF match node " << "2: "  << match[2];
			BOOST_LOG_TRIVIAL(debug) << "TGF match node " << "3: "  << match[3];
			BOOST_LOG_TRIVIAL(debug) << "TGF match node " << "4: " << match[4];
			BOOST_LOG_TRIVIAL(debug) << "TGF match node " << "5: " << match[5];
			BOOST_LOG_TRIVIAL(debug) << "TGF match node " << "6: " << match[6];
			BOOST_LOG_TRIVIAL(debug) << "TGF match node " << "7: " << match[7];
			BOOST_LOG_TRIVIAL(debug) << "TGF match node " << "8: " << match[8];
			if(!impltype.compare(SoundComponents::ImplTypeNames[SoundComponents::HW])){

				int slot = boost::lexical_cast<int>(match[5]);

				patch->createSoundComponent(uid, type, paramtokens, slot);

			}else if(!impltype.compare(SoundComponents::ImplTypeNames[SoundComponents::SW])){

				patch->createSoundComponent(uid, type, paramtokens);

			}else{

				BOOST_LOG_TRIVIAL(error) << "unknown implementation type";
			}
		}

		// match edge
		if (boost::regex_match(line, match, edgeexpr)){

			BOOST_LOG_TRIVIAL(debug) << "TGF match edge " << "1: " << match[1];
			BOOST_LOG_TRIVIAL(debug) << "TGF match edge " << "2: " << match[2];
			BOOST_LOG_TRIVIAL(debug) << "TGF match edge " << "3: " << match[3];
			BOOST_LOG_TRIVIAL(debug) << "TGF match edge " << "4: " << match[4];

			int source_uid  = boost::lexical_cast<int>(match[1]);
			int dest_uid    = boost::lexical_cast<int>(match[2]);
			int source_port = boost::lexical_cast<int>(match[3]);
			int dest_port   = boost::lexical_cast<int>(match[4]);

			patch->createSoundLink(source_uid, source_port, dest_uid, dest_port);
		}
	}


}



