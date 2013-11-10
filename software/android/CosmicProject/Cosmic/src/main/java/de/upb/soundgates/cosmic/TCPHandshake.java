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
public class TCPHandshake extends AsyncTask<Void, Void, Void> {
    protected String host;
    protected int port;

    protected AsyncTaskListener<String> listener;

    protected String result;
    protected String error;

    protected Socket s;

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

    protected Void doInBackground(Void... voids) {
        try {
            s = new Socket(host, port);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            out.println("getInteractiveComponents" + "\r\n");
            out.flush();

            result = in.readLine();
            Log.d(MainActivity.LOG_TAG, "empfangen: " + result);

            return null;
        } catch (UnknownHostException e) {
            error = "Don't know about host " + host + ":" + port;
            Log.e(MainActivity.LOG_TAG, error);
            listener.onAsyncTaskFailure(error);
            return null;
        } catch (IOException e) {
            error = "Couldn't get I/O for the connection to " + host + ":" + port;
            Log.e(MainActivity.LOG_TAG, error);
            listener.onAsyncTaskFailure(error);
            return null;
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
    protected void onPostExecute(Void result) {
        Log.d(MainActivity.LOG_TAG, "onPostExecute result: " + this.result);
        listener.onAsyncTaskCompletion(this.result);
    }
}
