package de.upb.soundgates.cosmic.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import de.upb.soundgates.cosmic.rows.InteractionMethod;
import de.upb.soundgates.cosmic.R;
import de.upb.soundgates.cosmic.osc.OSCMessage;
import de.upb.soundgates.cosmic.osc.OSCMessageStore;
import de.upb.soundgates.cosmic.rows.InteractionRow;

/**
 * Created by posewsky on 03.12.13.
 */
public class InteractionFragment extends ListFragment {
    private LayoutInflater inflater;
    private boolean isScrolling;

    public static InteractionFragment newInstance() {
        InteractionFragment fragment = new InteractionFragment();

        return fragment;
    }

    public InteractionFragment() {
        isScrolling = false;
    }

    public boolean isScrolling() {
        return isScrolling;
    }

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
            getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView absListView, int i) {
                    if(i == SCROLL_STATE_IDLE) {
                        isScrolling = false;
                    } else {
                        isScrolling = true;
                    }
                }

                @Override
                public void onScroll(AbsListView absListView, int i, int i2, int i3) {

                }
            });
        }
    }

    private class OSCMessageAdapter extends BaseAdapter {
        final List<InteractionRow> rows;

        // create rows for interaction according to model
        OSCMessageAdapter(List<OSCMessage> msgList) {
            rows = new ArrayList<InteractionRow>(msgList.size());

            for (OSCMessage msg : msgList) {
                InteractionRow ir = msg.getInteractionMethod().createRow(inflater, msg);
                if(ir != null) {
                    rows.add(ir);
                } else {
                    String err = "Interaction method of [" + msg.toStringFull() + "] unknown";
                    Log.e("InteractionFragment", err);
                    System.exit(-1);
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

        @Override
        public boolean areAllItemsEnabled() {
            Log.d("areAllItemsEnabled", isScrolling+"");
            return isScrolling;
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
