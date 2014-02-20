package de.upb.soundgates.cosmic.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import de.upb.soundgates.cosmic.MainActivity;
import de.upb.soundgates.cosmic.R;
import de.upb.soundgates.cosmic.adapters.SelectBindArrayAdapter;
import de.upb.soundgates.cosmic.osc.OSCMessage;
import de.upb.soundgates.cosmic.osc.OSCMessageStore;
import de.upb.soundgates.cosmic.sensor.AbstractSensorListener;

/**
 * Created by posewsky on 03.12.13.
 */
public class SelectFragment extends ListFragment {
    public static final String LOG_TAG = "Cosmic - SelectFragment";

    private Button selectButton;
    private CheckBox checkAll;

    public static SelectFragment newInstance() {
        SelectFragment fragment = new SelectFragment();
        return fragment;
    }

    public SelectFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.select2_main, container, false);

        selectButton = (Button) rootView.findViewById(R.id.select_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //unregister current Listeners
                AbstractSensorListener.unregisterListeners();

                for(Fragment f : getFragmentManager().getFragments()) {
                    if(f instanceof InteractionFragment) {
                        ((InteractionFragment)f).updateList();
                        break;
                    }
                }

                ((MainActivity)getActivity()).mViewPager.setCurrentItem(2);
            }
        });

        checkAll = (CheckBox) rootView.findViewById(R.id.checkAllBox);
        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                OSCMessageStore msg_store = OSCMessageStore.hasInstance();
                if(msg_store != null) {
                    for(OSCMessage msg : msg_store.getOSCMessageAsList())
                        msg.setSelected(b);
                    final ListView listView = getListView();
                    if(listView != null && listView.getAdapter() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((SelectBindArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });

        return rootView;
    }

    public void updateList()
    {
        OSCMessageStore msg_store = OSCMessageStore.hasInstance();
        if(msg_store != null)
        {
            SelectBindArrayAdapter adapter = new SelectBindArrayAdapter(getActivity(), msg_store.getOSCMessageAsList());
            setListAdapter(adapter);
        }
    }
}
