package de.upb.soundgates.cosmic.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.upb.soundgates.cosmic.adapters.BindArrayAdapter;
import de.upb.soundgates.cosmic.CosmicPreferences;
import de.upb.soundgates.cosmic.R;
import de.upb.soundgates.cosmic.osc.OSCMessageStore;

/**
 * Created by posewsky on 03.12.13.
 */
public class BindFragment extends ListFragment {
    public static BindFragment newInstance() {
        BindFragment fragment = new BindFragment();

        return fragment;
    }

    public BindFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bind_main, container, false);

        return rootView;
    }

    public void updateList() {
        OSCMessageStore msg_store = OSCMessageStore.hasInstance();
        if(msg_store != null)
        {
            CosmicPreferences prefs = new CosmicPreferences(getActivity());
            String host = prefs.getString("current_host");
            int port = prefs.getInt("current_port");

            BindArrayAdapter adapter = new BindArrayAdapter(getActivity(), msg_store.getSelectedOSCMessageAsList(), host, port);
            setListAdapter(adapter);
        }
    }
}
