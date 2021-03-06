package com.wecandoit.jinju_0_0_2;

import java.util.*;

import android.app.*;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.*;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.util.*;
import android.view.*;
import android.widget.*;

import com.wecandoit.jinju_0_0_2.R;
import com.wecandoit.jinju_mech_lib.*;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {
	private final String TAG = "jinju_v0.0.2::MainActivity";
	public static ClientThread jLog;

	public static int tab_popular = 0;
	public static int tab_recent = 1;
	public static int tab_favorite = 2;
	public static int tab_myfile = 3;
	public static int tab_myjob = 4;
	public static int tab_search = 5;

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	// action bar
	private ActionBar mActionBar;
	MenuItem mSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String ip = "logio.test.clipeo.com", port = "28177";
		jLog = new ClientThread("jinju", ip, port);
		new Thread(jLog).start();
		Log.d(TAG, "jLog is start!!");

		// Set up the action bar.
		mActionBar = getActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						mActionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			mActionBar.addTab(mActionBar.newTab()
					// .setText(mSectionsPagerAdapter.getPageTitle(i))
					.setIcon(mSectionsPagerAdapter.getPageIcon(i))
					.setTabListener(this));
		}
		mActionBar.setSelectedNavigationItem(tab_popular);
		if (jLog.isConnect) {
			String str = String.format("jLog is connected : %s : %s", ip, port);
			Log.d(TAG, str);
			jLog.d(str);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_actions, menu);

		// Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		mSearch = menu.findItem(R.id.action_search);
		mSearch.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
			public boolean onMenuItemActionCollapse(MenuItem item) {
				jLog.d("현재 상태 : 축소됨");
				return true;
			}

			public boolean onMenuItemActionExpand(MenuItem item) {
				jLog.d("현재 상태 : 확장됨");
				return true;
			}
		});

		SearchView sv = (SearchView) mSearch.getActionView();

		sv.setQueryHint("Find from Youtube");
		sv.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));

		sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			public boolean onQueryTextSubmit(String query) {
				jLog.d(query + "를 검색합니다.");
				mActionBar.setSelectedNavigationItem(tab_search);
				return true;
			}

			public boolean onQueryTextChange(String newText) {
				// jLog.d("검색식 : " + newText);
				return true;
			}
		});

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_search:
			// search action
			return true;
		case R.id.action_location_found:
			// location found
			LocationFound();
			return true;
		case R.id.action_refresh:
			// refresh
			// refreshMenuItem = item;
			// load the data from server
			// new SyncData().execute();
			return true;
		case R.id.action_help:
			// help action
			return true;
		case R.id.action_check_updates:
			// check for updates action
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Launching new activity
	 * */
	private void LocationFound() {
		Intent i = new Intent(MainActivity.this, LocationFound.class);
		startActivity(i);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		int idx = tab.getPosition();
		String strTitle = "SMP/" + mSectionsPagerAdapter.getPageTitle(idx);
		getActionBar().setTitle(strTitle);
		mViewPager.setCurrentItem(idx);
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
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
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 6;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.tab_popular).toUpperCase(l);
			case 1:
				return getString(R.string.tab_recent).toUpperCase(l);
			case 2:
				return getString(R.string.tab_favorite).toUpperCase(l);
			case 3:
				return getString(R.string.tab_myfile).toUpperCase(l);
			case 4:
				return getString(R.string.tab_myjob).toUpperCase(l);
			case 5:
				return getString(R.string.tab_search).toUpperCase(l);
			}
			return null;
		}

		public int getPageIcon(int position) {
			switch (position) {
			case 0:
				return R.drawable.tab_popular;
			case 1:
				return R.drawable.tab_recent;
			case 2:
				return R.drawable.tab_favorite;
			case 3:
				return R.drawable.tab_myfile;
			case 4:
				return R.drawable.tab_myjob;
			case 5:
				return R.drawable.tab_search;
			}
			return R.drawable.tab_myjob;
		}

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			return rootView;
		}
	}

}
