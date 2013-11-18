package de.upb.soundgates.cosmic;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.util.Log;

public class SocketSender extends AsyncTask<String, Void, Void> {
    private static final String TAG = "SocketSender";

    private String host;
    private int port;


    public SocketSender(String host, int port) {
        this.host = host;
        this.port = port;
    }


    private void sendMessage(String message)
    {
        DatagramSocket s = null;
        try{

            Log.i(TAG, "Sending "+ message+" to " + host + ":" + port);


            s = new DatagramSocket();
            InetAddress local = InetAddress.getByName(host);
            int msg_length = message.length();
            byte[] messageb = message.getBytes();
            DatagramPacket p = new DatagramPacket(messageb, msg_length, local, port);
            s.send(p);
            //printWriter.print(message);
            //printWriter.flush();

        } catch(UnknownHostException e) {
            System.out.println("Unknown host: " + host);

        } catch(IOException e) {
            System.out.println("No I/O");
        } finally {
            if(s != null)
                s.close();
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