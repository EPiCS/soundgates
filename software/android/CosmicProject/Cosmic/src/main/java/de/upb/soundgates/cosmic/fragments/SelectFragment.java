package de.upb.soundgates.cosmic.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.upb.soundgates.cosmic.MainActivity;
import de.upb.soundgates.cosmic.R;
import de.upb.soundgates.cosmic.adapters.SelectBindArrayAdapter;
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
        View rootView = inflater.inflate(R.layout.select2_main, container, false);

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

                OSCMessageStore msg_store = OSCMessageStore.hasInstance();
                if(msg_store != null)
                {
                    Log.d(LOG_TAG, msg_store.toStringFull());
                }

                ((MainActivity)getActivity()).mViewPager.setCurrentItem(2);
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
