package de.upb.soundgates.cosmic.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

import de.upb.soundgates.cosmic.AsyncTaskListener;
import de.upb.soundgates.cosmic.CosmicPreferences;
import de.upb.soundgates.cosmic.MainActivity;
import de.upb.soundgates.cosmic.R;
import de.upb.soundgates.cosmic.TCPHandshake;
import de.upb.soundgates.cosmic.osc.OSCMessageStore;

/**
 * Created by posewsky on 03.12.13.
 */
public class ConnectFragment extends Fragment implements View.OnClickListener, AsyncTaskListener<String> {
    public static final String LOG_TAG = "Cosmic - ConnectFragment";

    public static final String OSC_MSG_DELIMITER = "\\|\\|"; // always as regex!

    private AutoCompleteTextView ipTextView;
    private AutoCompleteTextView portTextView;

    String host;
    int port;

    private TCPHandshake hs;

    public static ConnectFragment newInstance() {
        ConnectFragment fragment = new ConnectFragment();
        return fragment;
    }

    public ConnectFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.connect_main, container, false);

        CosmicPreferences prefs = new CosmicPreferences(getActivity());

        Set<String> hosts = prefs.getStringSet("hosts");
        String[] hostarray = new String[hosts.size()];
        hosts.toArray(hostarray);

        Set<String> ports = prefs.getStringSet("ports");
        String[] portarray = new String[ports.size()];
        ports.toArray(portarray);

        ipTextView = (AutoCompleteTextView) rootView.findViewById(R.id.ip_edittext);
        ipTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    ipTextView.showDropDown();
                }
            }
        });

        ArrayAdapter<String> hostAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, hostarray);
        ipTextView.setAdapter(hostAdapter);

        portTextView = (AutoCompleteTextView) rootView.findViewById(R.id.port_edittext);
        portTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    portTextView.showDropDown();
                }
            }
        });
        ArrayAdapter<String> portAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, portarray);
        portTextView.setAdapter(portAdapter);

        Button connectButton = (Button) rootView.findViewById(R.id.connect_button);
        connectButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        host = ipTextView.getText().toString();

        try {
            port = Integer.parseInt(portTextView.getText().toString());
        } catch(java.lang.NumberFormatException nfe) {
            AlertDialog portAlert = new AlertDialog.Builder(getActivity()).create();
            portAlert.setTitle("Port error");
            portAlert.setMessage("Port \"" + portTextView.getText().toString() + "\" is not valid");
            portAlert.show();
            return;
        }

        hs = new TCPHandshake(host, port, this);
        hs.execute();

        // TODO: Inform user about progress
    }

    @Override
    public void onAsyncTaskCompletion(String result) {
        OSCMessageStore.removeInstance();
        OSCMessageStore msg_store = OSCMessageStore.getInstance();
        for(String msg : result.split(OSC_MSG_DELIMITER)) {
            if(!msg_store.addOSCMessage(msg)) {
                Log.e(LOG_TAG, "Couldn't add Message:" + msg);
            }
        }

        CosmicPreferences prefs = new CosmicPreferences(getActivity());
        prefs.putString("current_host", host); // setHost(host);
        prefs.putInt("current_port", port); // setPort(port);

        // add host to list of known hosts
        Set<String> hosts = prefs.getStringSet("hosts");
        hosts.add(host);
        prefs.putStringSet("hosts", hosts);

        // add port to list of known ports
        Set<String> ports = prefs.getStringSet("ports");
        ports.add(port + "");
        prefs.putStringSet("ports", ports);

        for(Fragment f : getFragmentManager().getFragments()) {
            if(f instanceof SelectFragment) {
                ((SelectFragment)f).updateList();
                break;
            }
        }

        ((MainActivity)getActivity()).mViewPager.setCurrentItem(1);
    }

    @Override
    public void onAsyncTaskFailure(String error) {
        final String msg = error;
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Context context = getActivity();
                CharSequence text = "Error: " + msg.replaceAll("\n", "");
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }
}
