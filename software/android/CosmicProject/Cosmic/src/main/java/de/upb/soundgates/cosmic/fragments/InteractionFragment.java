package de.upb.soundgates.cosmic.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import de.upb.soundgates.cosmic.adapters.BindArrayAdapter;
import de.upb.soundgates.cosmic.CosmicPreferences;
import de.upb.soundgates.cosmic.R;
import de.upb.soundgates.cosmic.osc.OSCMessage;
import de.upb.soundgates.cosmic.osc.OSCMessageStore;
import de.upb.soundgates.cosmic.rows.InteractionRow;
import de.upb.soundgates.cosmic.rows.InteractionSeekBarRow;

/**
 * Created by posewsky on 03.12.13.
 */
public class InteractionFragment extends ListFragment {
    public static InteractionFragment newInstance() {
        InteractionFragment fragment = new InteractionFragment();

        return fragment;
    }

    public InteractionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.interaction_main, container, false);

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

    private class OSCMessageAdapter extends BaseAdapter {
        final List<InteractionRow> rows;

        OSCMessageAdapter(List<OSCMessage> msgs) {
            rows = new ArrayList<InteractionRow>(msgs.size());

            for (OSCMessage msg : msgs) {
                switch (msg.getInteractionMethod())
                {
                    case SEEKBAR:
                        rows.add(new InteractionSeekBarRow(LayoutInflater.from(), msg))
                        break;
                    case TILT:
                        break;
                    case SHAKE:
                        break;
                }
            }
        }
    }
}
