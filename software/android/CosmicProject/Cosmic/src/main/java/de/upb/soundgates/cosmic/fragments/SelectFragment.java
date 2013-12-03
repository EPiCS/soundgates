package de.upb.soundgates.cosmic.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import de.upb.soundgates.cosmic.MainActivity;
import de.upb.soundgates.cosmic.R;
import de.upb.soundgates.cosmic.osc.OSCMessage;
import de.upb.soundgates.cosmic.osc.OSCMessageStore;

/**
 * Created by posewsky on 03.12.13.
 */
public class SelectFragment extends ListFragment {
    public static final String LOG_TAG = "Cosmic - SelectFragment";

    private Button selectButton;

    public static SelectFragment newInstance() {
        SelectFragment fragment = new SelectFragment();
        return fragment;
    }

    public SelectFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.select_main, container, false);

        selectButton = (Button) rootView.findViewById(R.id.select_button);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Fragment f : getFragmentManager().getFragments()) {
                    if(f instanceof BindFragment) {
                        ((BindFragment)f).updateList();
                        break;
                    }
                }

                ((MainActivity)getActivity()).mViewPager.setCurrentItem(2);
            }
        });

        return rootView;
    }

    public void updateList()
    {
        if(OSCMessageStore.hasInstance() != null)
        {
            ArrayAdapter<OSCMessage> adapter =
                    new ArrayAdapter<OSCMessage>(
                            getActivity(),
                            android.R.layout.simple_list_item_multiple_choice,
                            OSCMessageStore.getInstance().getOSCMessageAsList()
                    );
            setListAdapter(adapter);

            getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                    // AdapterView is the parent class of ListView
                    ListView lv = (ListView) arg0;
                    OSCMessageStore.getInstance().markOSCMessage(position, lv.isItemChecked(position));
                    Log.d(LOG_TAG, OSCMessageStore.getInstance().toString());
                }
            });
        }
    }
}
