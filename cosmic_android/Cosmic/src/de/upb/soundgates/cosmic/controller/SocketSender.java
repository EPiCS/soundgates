package de.upb.soundgates.cosmic.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketSender {
	
	private static final String FPGAIP		= "192.168.31.1";
	private static final int    FPGAPORT	= 8888;
	
	
	static void sendMessage(String message)
	{
		Socket client;

		try{
		   client = new Socket(FPGAIP, FPGAPORT);
		   PrintWriter printWriter = 
				   new PrintWriter(
						   new OutputStreamWriter(client.getOutputStream()));
		   printWriter.print(message);
		   printWriter.flush();

		} catch(UnknownHostException e) {
		   System.out.println("Unknown host: " + FPGAIP);

		} catch(IOException e) {
		   System.out.println("No I/O");
		}
	}

}
