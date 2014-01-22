package de.upb.soundgates.cosmic.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import de.upb.soundgates.cosmic.InteractionMethod;
import de.upb.soundgates.cosmic.adapters.BindArrayAdapter;
import de.upb.soundgates.cosmic.CosmicPreferences;
import de.upb.soundgates.cosmic.R;
import de.upb.soundgates.cosmic.osc.OSCMessage;
import de.upb.soundgates.cosmic.osc.OSCMessageStore;
import de.upb.soundgates.cosmic.rows.InteractionLightRow;
import de.upb.soundgates.cosmic.rows.InteractionRow;
import de.upb.soundgates.cosmic.rows.InteractionSeekBarRow;
import de.upb.soundgates.cosmic.rows.InteractionShakeRow;
import de.upb.soundgates.cosmic.rows.InteractionTiltRow;

/**
 * Created by posewsky on 03.12.13.
 */
public class InteractionFragment extends ListFragment {
    private LayoutInflater inflater;

    public static InteractionFragment newInstance() {
        InteractionFragment fragment = new InteractionFragment();

        return fragment;
    }

    public InteractionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.interaction_main, container, false);

        return rootView;
    }

    public void updateList() {
        OSCMessageStore msg_store = OSCMessageStore.hasInstance();
        if(msg_store != null)
        {
            OSCMessageAdapter adapter = new OSCMessageAdapter(msg_store.getSelectedOSCMessageAsList());
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
                        rows.add(new InteractionSeekBarRow(inflater, msg));
                        break;
                    case TILT:
                        rows.add(new InteractionTiltRow(inflater, msg));
                        break;
                    case SHAKE:
                        rows.add(new InteractionShakeRow(inflater, msg));
                        break;
                    case LIGHT:
                        rows.add(new InteractionLightRow(inflater, msg));
                        break;
                }
            }
        }

        @Override
        public int getViewTypeCount() {
            return InteractionMethod.values().length;
        }

        @Override
        public int getItemViewType(int position) {
            return rows.get(position).getViewType();
        }

        public int getCount() {
            return rows.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            return rows.get(position).getView(convertView);
        }
    }
}
