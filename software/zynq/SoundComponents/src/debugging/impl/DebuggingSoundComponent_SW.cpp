/*
 * DebuggingSoundComponent_SW.cpp
 *
 *  Created on: Jan 23, 2014
 *      Author: CaiusC
 */

#include "DebuggingSoundComponent_SW.hpp"

DebuggingSoundComponent_SW::DebuggingSoundComponent_SW(std::vector<std::string> params) : DebuggingSoundComponent(params)
{
	// implementation specific initialization stuff here
}

// The actual software processing
// In this example, we take an incoming control value and write it to each sample on the sound output
// and take an incoming sound signal, average over the samples and write it to the control output
void DebuggingSoundComponent_SW::process()
{
	/* Get current time */
	boost::posix_time::ptime now = boost::posix_time::microsec_clock::universal_time();

	/* Calculate difference between last execution and now */
	m_TurnaroundDuration = now - m_LastExecutionTime;
	/* Create udp objects */
	using boost::asio::ip::udp;
	boost::asio::io_service io_service;
	udp::resolver resolver(io_service);
	udp::resolver::query query(udp::v4(), "localhost", "1338");
    udp::endpoint receiver_endpoint = *resolver.resolve(query);
    udp::socket socket(io_service);
    socket.open(udp::v4());
	/* Create Buffer */
    /* ------------- */
    /*  char: index  */
    /* ------------- */
    /*  int32: pcm   */
    /* ------------- */
	boost::array<char, sizeof(char) + sizeof(int)> send_buf;

	/* Write input directly to output and send it via UDP to localhost server*/
	for (int i = 0; i < Synthesizer::config::blocksize; i++ ) {
		int valueA = (*m_DebuggingSoundIn_1_Port)[i];
		m_DebuggingSoundOut_1_Port->writeSample( valueA, i );
		std::copy(reinterpret_cast<char*>(&valueA), reinterpret_cast<char*>(&valueA) + sizeof(int), &send_buf[0]);
		/* Send Buffer via UDP Socket */
		send_buf[4] = (char) i;
	    socket.send_to( boost::asio::buffer(send_buf), receiver_endpoint );
	}
	socket.close();
	m_LastExecutionTime = boost::posix_time::microsec_clock::universal_time();
	m_ExecutionDuration = m_LastExecutionTime - now;

	/* Generate float Values in milliseconds*/
	long turnaround = m_TurnaroundDuration.total_microseconds();
	long execution_duration = m_ExecutionDuration.total_microseconds();
	/* Send values via sockets */
	/*TODO: IF CONTROL_PORT == ETHERNET */


}
