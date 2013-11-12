package de.upb.soundgates.cosmic;

import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.*;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import de.upb.soundgates.cosmic.osc.OSCMessage;
import de.upb.soundgates.cosmic.osc.OSCMessageStore;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {
    public static final String LOG_TAG = "Cosmic - MainActivity";

    public static OSCMessageStore msg_store;
    public static final String OSC_MSG_DELIMITER = "\\|\\|"; // always as regex!

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            if(position == 0)
                return ConnectFragment.newInstance();
            else if(position == 1)
                return SelectFragment.newInstance();
            else if(position == 2)
                return BindFragment.newInstance();
            else
                return null;
        }

        @Override
        public long getItemId(int position) {
            return (long)position;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_connect).toUpperCase(l);
                case 1:
                    return getString(R.string.title_select).toUpperCase(l);
                case 2:
                    return getString(R.string.title_bind).toUpperCase(l);
            }
            return null;
        }
    }

    public static class ConnectFragment extends Fragment implements OnClickListener, AsyncTaskListener<String> {
        private TextView ipTextView;
        private TextView portTextView;
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

            ipTextView = (TextView) rootView.findViewById(R.id.ip_edittext);

            portTextView = (TextView) rootView.findViewById(R.id.port_edittext);

            Button connectButton = (Button) rootView.findViewById(R.id.connect_button);
            connectButton.setOnClickListener(this);

            return rootView;
        }

        @Override
        public void onClick(View view) {
            String host = ipTextView.getText().toString();
            int port = -1;

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
            msg_store = new OSCMessageStore();
            for(String msg : result.split(OSC_MSG_DELIMITER)) {
                msg_store.addOSCMessage(msg);
                Log.d(MainActivity.LOG_TAG, "Message " + msg);
            }

            for(Fragment f : getFragmentManager().getFragments()) {
                if(f instanceof SelectFragment)
                    ((SelectFragment)f).updateList();
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

    public static class SelectFragment extends ListFragment {
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

            selectButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)getActivity()).mViewPager.setCurrentItem(2);
                }
            });

            return rootView;
        }

        public void updateList()
        {
            if(msg_store != null)
            {
                ArrayAdapter<OSCMessage> adapter =
                        new ArrayAdapter<OSCMessage>(
                                getActivity(),
                                android.R.layout.simple_list_item_multiple_choice,
                                msg_store.getOSCMessageAsList()
                        );
                setListAdapter(adapter);

                getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                        // AdapterView is the parent class of ListView
                        ListView lv = (ListView) arg0;
                        msg_store.markOSCMessage(position, lv.isItemChecked(position));
                        Log.d(MainActivity.LOG_TAG, msg_store.toString());
                    }
                });
            }
        }
    }

    public static class BindFragment extends Fragment {
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
    }

}
