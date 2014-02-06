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
//	boost::asio::io_service io_service;
//	using boost::asio::ip::udp;
//	udp::endpoint local_endpoint;
//	local_endpoint = udp::endpoint(boost::asio::ip::address::from_string("127.0.0.1"),  boost::lexical_cast<int>(1338));
//	udp::socket socket(io_service);
//	socket.open(udp::v4());
//	socket.bind(local_endpoint);

	/* Resolve IP */
	boost::asio::io_service io_service;
	using boost::asio::ip::tcp;
	tcp::resolver resolver(io_service);
	tcp::resolver::query query("127.0.0.1", "1338");
	tcp::resolver::iterator endpoint_iterator = resolver.resolve(query);
	tcp::resolver::iterator end;
	/* Create Socket */
	tcp::socket socket(io_service);
	boost::system::error_code error = boost::asio::error::host_not_found;
	while (error && endpoint_iterator != end) {
		socket.close();
		socket.connect(*endpoint_iterator++, error);
	}
	if (error)
		throw boost::system::system_error(error);


	/* Write input directly to output and send it via UDP to localhost server*/
	for (int i = 0; i < Synthesizer::config::blocksize; i++ ) {
		int valueA = (*m_DebuggingSoundIn_1_Port)[i];
		m_DebuggingSoundOut_1_Port->writeSample(valueA, i);
		/* Send value to server */
		boost::array<char, sizeof(int)> send_buf;
		std::copy(reinterpret_cast<char*>(&valueA), reinterpret_cast<char*>(&valueA) + sizeof(int), &send_buf[0]);
		//socket.send_to(boost::asio::buffer( send_buf ), *iterator);
		socket.send( boost::asio::buffer( send_buf) );
		//boost::asio::write(socket, boost::asio::buffer( send_buf));
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
