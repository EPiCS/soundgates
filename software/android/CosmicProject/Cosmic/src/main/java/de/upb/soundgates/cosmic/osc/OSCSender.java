package de.upb.soundgates.cosmic.osc;

import android.os.AsyncTask;
import android.util.Log;

import com.illposed.osc.OSCPortOut;
import com.illposed.osc.OSCMessage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by posewsky on 15.11.13.
 */
public class OSCSender extends AsyncTask<Void, Void, Boolean> {
    protected String host;
    protected int port;

    protected OSCPortOut sender;
    protected OSCMessage msg;

    private String error;

    public OSCSender(String host, int port, OSCMessage msg) {
        super();

        this.host = host;
        this.port = port;

        this.msg  = msg;
        sender    = null;

        error     = null;
    }

    protected void cleanup() {
        if(sender != null) {
            sender.close();
        }
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            sender = new OSCPortOut(InetAddress.getByName(host), port);
            sender.send(msg);
            return true;
        } catch (SocketException e) {
            error = e.toString();
            return false;
        } catch (UnknownHostException e) {
            error = "Don't know about host " + host + ":" + port;
            return false;
        } catch (IOException e) {
            error = "Couldn't get I/O for the connection to " + host + ":" + port;
            return false;
        } finally {
            cleanup();
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if(success)
            Log.d("OSCSender", "gesendet");
        else
            Log.e("OSCSender", error);
    }
}
