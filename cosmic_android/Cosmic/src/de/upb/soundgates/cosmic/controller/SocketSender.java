package de.upb.soundgates.cosmic.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.util.Log;

public class SocketSender extends AsyncTask<String, Void, Void> {
	private static final String TAG			= "SocketSender";
	//private static final String FPGAIP		= "192.168.31.1";
	private static final String FPGAIP		= "10.0.2.2";
	private static final int    FPGAPORT	= 5005;


	private void sendMessage(String message)
	{
		try{

			Log.i(TAG, "Sending "+ message+" to " + FPGAIP + ":" + FPGAPORT);

			
			DatagramSocket s = new DatagramSocket();
	        InetAddress local = InetAddress.getByName(FPGAIP);
	        int msg_length = message.length();
	        byte[] messageb = message.getBytes();
	        DatagramPacket p = new DatagramPacket(messageb, msg_length, local,
	                FPGAPORT);
	        s.send(p);
			
			//printWriter.print(message);
			//printWriter.flush();

		} catch(UnknownHostException e) {
			System.out.println("Unknown host: " + FPGAIP);

		} catch(IOException e) {
			System.out.println("No I/O");
		}
	}


	@Override
	protected Void doInBackground(String... messages) {
		// TODO: Checking for just one string - should we support multiple Strings?
		int count = messages.length;
		if(count != 1)
		{
			Log.e(TAG, "More than one String is illegal");
			return null;
		}
		sendMessage(messages[0]);
		return null;
	}
	
	 protected void onPostExecute() {
         Log.i("TAG", "message has been send");
     }


}
