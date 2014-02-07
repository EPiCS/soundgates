/*
 * OSCService.h
 *
 *  Created on: Dec 5, 2013
 *      Author: lukas
 */

#ifndef OSCSERVICE_H_
#define OSCSERVICE_H_



#include <vector>
#include <lo/lo.h>
#include <boost/lexical_cast.hpp>


#include "../UIService.h"
#include "../../Patch.h"
#include "../../core/Synthesizer.h"
#include "../../InputSoundComponent.h"
#include "../../core/SoundgatesConfig.h"


using namespace std;

namespace ui {


extern "C" int genericOSCHandler(const char *path, const char *types, lo_arg ** argv, int argc, void *data, void *user_data);

class OSCService : public UIService{

private:

	Patch&	            m_pPatch;
	lo_server_thread	m_LoServerThread;

	void error(int num, const char *m, const char *path);

public:

	OSCService(Patch& patch);

	virtual ~OSCService();

	void startService();

	void stopService();

};

} /* namespace ui */

#endif /* OSCSERVICE_H_ */
