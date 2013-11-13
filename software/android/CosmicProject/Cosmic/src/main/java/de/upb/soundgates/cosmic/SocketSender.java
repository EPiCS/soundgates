package de.upb.soundgates.cosmic;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.util.Log;


/**
 * Created by posewsky on 13.11.13.
 */
public class SocketSender extends AsyncTask<String, Void, Void> {
    private String ip;
    private int port;

    public SocketSender(String ip, int port) {
        this.ip     = ip;
        this.port   = port;
    }

    private void sendMessage(String message)
    {
        try{
            Log.i(MainActivity.LOG_TAG, "Sending " + message + " to " + ip + ":" + port);


            DatagramSocket s = new DatagramSocket();
            InetAddress local = InetAddress.getByName(ip);
            int msg_length = message.length();
            byte[] messageb = message.getBytes();
            DatagramPacket p = new DatagramPacket(messageb, msg_length, local, port);
            s.send(p);
        } catch(UnknownHostException e) {
            Log.e(MainActivity.LOG_TAG, "Unknown host: " + ip);
        } catch(IOException e) {
            Log.e(MainActivity.LOG_TAG, "No I/O");
        }
    }


    @Override
    protected Void doInBackground(String... messages) {
        // TODO: Checking for just one string - should we support multiple Strings?
        int count = messages.length;
        if(count != 1)
        {
            Log.e(MainActivity.LOG_TAG, "More than one String is illegal");
            return null;
        }
        sendMessage(messages[0]);
        return null;
    }

    protected void onPostExecute() {
        Log.i(MainActivity.LOG_TAG, "message has been send");
    }


}
