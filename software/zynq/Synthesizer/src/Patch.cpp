/*
 * Patch.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */


#include "Patch.h"


void addSoundComponent(SoundComponent*);
void connectSoundComponent(SoundComponent*, Port*, SoundComponent*, Port*);

void Patch::createFromFile(std::string filename){


}



void Patch::createSoundComponent(int uid, std::string type, std::vector<std::string> parameters){

	std::cout << "Creating software component." << std::endl;
	std::cout << "Uid: " << uid << std::endl << "Type: " << type << std::endl << "Parameters: " << parameters << std::endl;


}
void Patch::createSoundComponent(int uid, std::string type, int slot, std::vector<std::string> parameters){

	std::cout << "Creating hardware component." << std::endl;
	std::cout << "Uid: " << uid << std::endl << "Type: " << type << std::endl;
	std::cout << "Parameters: " << parameters << std::endl << "Slot: " << slot << std::endl;

}
