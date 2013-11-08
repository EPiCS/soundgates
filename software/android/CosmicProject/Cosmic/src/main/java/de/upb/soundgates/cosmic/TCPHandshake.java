package de.upb.soundgates.cosmic;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by posewsky on 08.11.13.
 */
public class TCPHandshake extends AsyncTask<Void, Void, String> {
    protected String host;
    protected int port;
    protected String[] components;
    protected String error;

    public TCPHandshake(String host, int port) {
        this.host = host;
        this.port = port;
        components= null;
        error     = null;
    }

    public String[] getInteractiveComponents()
    {
        return components;
    }

    protected String getError()
    {
        return error;
    }

    protected String doInBackground(Void... voids) {
        try {
            Socket s = new Socket(host, port);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            out.println("getInteractiveComponents");

            String recvString;
            String components = "";

            while((recvString = in.readLine()) != null) {
                components += recvString;
            }

            s.close();

           return components;
        } catch (UnknownHostException e) {
            error = "Don't know about host " + host + ":" + port;
            Log.e(MainActivity.LOG_TAG, error);
            cancel(true);
            return null;
        } catch (IOException e) {
            error = "Couldn't get I/O for the connection to " + host + ":" + port;
            Log.e(MainActivity.LOG_TAG, error);
            cancel(true);
            return null;
        }
    }

    protected void onPostExecute(String result) {
        if(result != null)
        {

        }
    }
}
