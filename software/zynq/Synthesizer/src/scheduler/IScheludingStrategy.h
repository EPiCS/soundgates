/*
 * IScheludingStrategy.h
 *
 *  Created on: Mar 3, 2014
 *      Author: lukas
 */

#ifndef ISCHELUDINGSTRATEGY_H_
#define ISCHELUDINGSTRATEGY_H_

class IScheludingStrategy {
public:
    virtual ~IScheludingStrategy();

    IScheludingStrategy();

    virtual void CalculateSchedule() = 0;

};

#endif /* ISCHELUDINGSTRATEGY_H_ */
