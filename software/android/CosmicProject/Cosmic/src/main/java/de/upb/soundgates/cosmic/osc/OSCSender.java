package de.upb.soundgates.cosmic.osc;

import android.os.AsyncTask;
import android.util.Log;

import com.illposed.osc.OSCPortOut;
import com.illposed.osc.OSCMessage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import de.upb.soundgates.cosmic.CosmicPreferences;

/**
 * Created by posewsky on 15.11.13.
 */
public class OSCSender extends AsyncTask<OSCMessage, Void, Boolean> {
    public static final String LOG_TAG = "Cosmic - OSCSender";
    protected OSCPortOut sender;

    private String error;

    private OSCSender() {
        super();

        sender    = null;

        error     = null;
    }

    protected void cleanup() {
        if(sender != null) {
            sender.close();
        }
    }

    public static void send(de.upb.soundgates.cosmic.osc.OSCMessage... messages) {
        int count = messages.length;
        OSCMessage msgArray[] = new OSCMessage[count];

        for(int i = 0; i < count; ++i) {
            de.upb.soundgates.cosmic.osc.OSCMessage msg = messages[i];
            OSCMessage packedMsg;

            int numParams = msg.getTypes().size();

            if(numParams > 0) {
                Object[] args = new Object[numParams];

                for(int j = 0; j < numParams; ++j)
                {
                    OSCType param = msg.getTypes().get(j);
                    switch(param.getTypeTag())
                    {
                        case 'i':
                            args[0] = new Integer((int)param.value);
                            break;
                        case 'f':
                            args[0] = new Float(param.value);
                            break;
                        default:
                            Log.e(LOG_TAG, "TypeTag not recognized");
                            return;
                    }
                }

                packedMsg = new com.illposed.osc.OSCMessage(msg.getPath(),args);
            } else {
                packedMsg = new com.illposed.osc.OSCMessage(msg.getPath());
            }

            msgArray[i] = packedMsg;
        }

        new OSCSender().execute(msgArray);
        for(de.upb.soundgates.cosmic.osc.OSCMessage msg : messages)
            Log.d(LOG_TAG, msg.toString());
    }

    @Override
    protected Boolean doInBackground(OSCMessage... messages) {
        String host = null;
        int port = -1;

        try {
            CosmicPreferences prefs = CosmicPreferences.getInstance(null);
            host = prefs.getString("current_host");
            port = prefs.getInt("current_port");

            sender = new OSCPortOut(InetAddress.getByName(host), port);
            for (OSCMessage msg : messages)
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
            Log.d(LOG_TAG, "gesendet");
        else
            Log.e(LOG_TAG, error);
    }
}
