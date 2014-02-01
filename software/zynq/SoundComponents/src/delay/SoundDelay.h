/*
 * SoundDelay.h
 *
 *  Created on: Dec 6, 2013
 *      Author: lukas
 */

#ifndef SOUNDDELAY_H_
#define SOUNDDELAY_H_

#include <cstring>

#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlLink.h>
#include <ControlPort.h>
#include <BufferedLink.h>
#include <Listener.h>

class SoundDelayComponent: public SoundComponentImpl {

private:

	float delayValue;
	int   m_DelaySlotSize;      /*< maximum number of samples for delay buffer */

	int* m_pDelaySlot;			/*< sample buffer for delay */
	int* m_pLastDelaySlot;		/*< pointer to the last delay slot */

	int* m_pReadPtr;			/* read pointer for ring buffer  */
	int* m_pWritePtr;			/* write pointer for ring buffer */

	inline int delayToSampleCount(float);

public:

	DECLARE_COMPONENTNAME

	DECLARE_PORT3(ControlPort, DelayIn, 1);
	DECLARE_PORT3(SoundPort, SoundIn, 2);
	DECLARE_PORT3(SoundPort, SoundOut, 1);

	SoundDelayComponent(std::vector<std::string> params);
	virtual ~SoundDelayComponent();

	void init(void);
	void process(void);


};

#endif /* SOUNDDELAY_H_ */
