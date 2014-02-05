package de.upb.soundgates.cosmic;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import de.upb.soundgates.cosmic.listeners.AsyncTaskListener;

/**
 * Created by posewsky on 08.11.13.
 */
public class TCPHandshake extends AsyncTask<Void, Void, Boolean> {
    protected String host;
    protected int port;

    protected AsyncTaskListener<String> listener;

    protected String result;
    protected String error;

    protected Socket s;

    protected final int TIMEOUT_IN_SEC = 10;

    public TCPHandshake(String host, int port, AsyncTaskListener<String> listener) {
        super();

        this.host = host;
        this.port = port;

        this.listener = listener;

        result    = null;
        error     = null;
        s         = null;
    }

    protected void cleanup() {
        if(s != null) {
            try {
                s.close();
            } catch (IOException e) {
            }
        }
    }

    protected Boolean doInBackground(Void... voids) {
        try {
            s = new Socket(host, port);

            s.setSoTimeout(TIMEOUT_IN_SEC * 1000);

            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            out.print("getInteractiveComponents");
            out.flush();

            result = in.readLine();

            if(result == null || result.length() == 0)
                throw new IOException("Received=["+result+"]");

            Log.d(MainActivity.LOG_TAG, "empfangen: " + result);

            return true;
        } catch (UnknownHostException e) {
            error = "Don't know about host " + host + ":" + port;
            Log.e(MainActivity.LOG_TAG, error);
            return false;
        } catch (SocketTimeoutException e) {
            error = "I/O Operation from " + host + ":" + port +  " took too long.";
            Log.e(MainActivity.LOG_TAG, error);
            return false;
        } catch (IOException e) {
            error = "Couldn't get I/O for the connection to " + host + ":" + port;
            Log.e(MainActivity.LOG_TAG, error);
            return false;
        } finally {
            cleanup();
        }
    }

    @Override
    protected void onCancelled() {
        error = "I/O for the connection to " + host + ":" + port + " took too long";
        Log.e(MainActivity.LOG_TAG, error);
        cleanup();
    }

    @Override
    protected void onPostExecute(Boolean success) {
        Log.d(MainActivity.LOG_TAG, "onPostExecute");
        if(success)
            listener.onAsyncTaskCompletion(result);
        else
            listener.onAsyncTaskFailure(error);
    }
}
