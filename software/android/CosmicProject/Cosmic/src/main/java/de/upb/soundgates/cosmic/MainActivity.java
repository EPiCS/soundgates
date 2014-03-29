package de.upb.soundgates.cosmic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.Locale;

import de.upb.soundgates.cosmic.fragments.*;
import de.upb.soundgates.cosmic.misc.DisableSlidingViewPager;
import de.upb.soundgates.cosmic.osc.OSCMessageStore;
import de.upb.soundgates.cosmic.sensors.AbstractSensorListener;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {
    public static final String LOG_TAG = "Cosmic - MainActivity";

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
    public DisableSlidingViewPager mViewPager;

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
        mViewPager = (DisableSlidingViewPager) findViewById(R.id.pager);
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

        //CosmicSensorManager csm = CosmicSensorManager.getInstance(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        CosmicPreferences prefs = CosmicPreferences.getInstance(this);
        prefs.remove("current_host");
        prefs.remove("current_port");
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
            case R.id.action_clearhistory:
                clearHistory();
                return true;
            case R.id.action_disconnect:
                disconnect();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // delete lists of known hosts and ports
    private void clearHistory() {
        CosmicPreferences prefs = CosmicPreferences.getInstance(this);
        prefs.putStringSet("hosts", null);
        prefs.putStringSet("ports", null);
    }

    // unregister current sensor listeners and clear lists
    private void disconnect() {
        AbstractSensorListener.unregisterListeners();

        OSCMessageStore.getInstance().clear();

        for(int i = 0; i < mSectionsPagerAdapter.getCount(); ++i) {
            Fragment f = mSectionsPagerAdapter.getFragmentByPosition(i);
            if(f instanceof SelectFragment) {
                ListView lv = ((SelectFragment) f).getListView();
                lv.deferNotifyDataSetChanged();
                lv.setAdapter(null);
            } else if(f instanceof InteractionFragment) {
                ((InteractionFragment) f).clear();
            }
        }

        mViewPager.setCurrentItem(0);
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

        // http://stackoverflow.com/questions/6976027/reusing-fragments-in-a-fragmentpageradapter
        public Fragment getFragmentByPosition(int pos) {
            String tag = "android:switcher:" + mViewPager.getId() + ":" + pos;
            return getSupportFragmentManager().findFragmentByTag(tag);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            if(position == 0)
                return ConnectFragment.newInstance();
            else if(position == 1)
                return SelectFragment.newInstance();
            else if(position == 2)
                return InteractionFragment.newInstance();
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
                    return getString(R.string.title_interaction).toUpperCase(l);
            }
            return null;
        }
    }
}
