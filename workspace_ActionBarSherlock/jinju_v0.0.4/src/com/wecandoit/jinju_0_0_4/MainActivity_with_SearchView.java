package com.wecandoit.jinju_0_0_4;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.sample.demos.*;
import com.actionbarsherlock.view.*;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.*;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.actionbarsherlock.widget.SearchView.OnSuggestionListener;
import com.wecandoit.jinju_0_0_4.R;
import com.wecandoit.jinju_mech_lib.*;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.app.*;
import android.content.*;
import android.content.res.Configuration;
import android.database.*;
import android.os.Bundle;
import android.provider.*;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.support.v4.view.GravityCompat;

public class MainActivity_with_SearchView extends SherlockFragmentActivity implements
		SearchView.OnQueryTextListener, SearchView.OnSuggestionListener {
	private final String TAG = "jinju_v0.0.2::MainActivity";
	public static ClientThread jLog;

	private static final String[] COLUMNS = { BaseColumns._ID,
			SearchManager.SUGGEST_COLUMN_TEXT_1, };
	private SuggestionsAdapter mSuggestionsAdapter;

	// Declare Variables
	DrawerLayout mDrawerLayout;
	ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;
	jNaviDraw_MenuListAdapter mMenuAdapter;
	String[] title;
	String[] subtitle;
	int[] icon;
	Fragment fragment_home = new Fragment_home();
	Fragment fragment_myfile = new Fragment_myfile();
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private ActionBar actionBar;
	MenuItem mSearch;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		setTheme(SampleList.THEME); // Used for theme switching in samples

		super.onCreate(savedInstanceState);
		// Get the view from drawer_main.xml
		setContentView(R.layout.drawer_main);

		String ip = "logio.test.clipeo.com", port = "28177";
		jLog = new ClientThread("jinju", ip, port);
		new Thread(jLog).start();
		Log.d(TAG, "jLog is start!!");

		// Get the Title
		mTitle = mDrawerTitle = getTitle();

		// Generate title
		title = new String[] { getString(R.string.side_home),
				getString(R.string.side_myfile),
				getString(R.string.side_speach),
				getString(R.string.side_music), getString(R.string.side_dance), };
		// Generate subtitle
		subtitle = title;// new String[] { "Subtitle Fragment 1",
							// "Subtitle Fragment 2" };

		// Generate icon
		icon = new int[] { R.drawable.side_home, R.drawable.side_myfile,
				R.drawable.side_dialog, R.drawable.side_music,
				R.drawable.side_dance, };

		// Locate DrawerLayout in drawer_main.xml
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// Locate ListView in drawer_main.xml
		mDrawerList = (ListView) findViewById(R.id.listview_drawer);

		// Set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// Pass string arrays to jNaviDraw_MenuListAdapter
		mMenuAdapter = new jNaviDraw_MenuListAdapter(MainActivity_with_SearchView.this, title,
				subtitle, icon);

		// Set the jNaviDraw_MenuListAdapter to the ListView
		mDrawerList.setAdapter(mMenuAdapter);

		// Capture listview menu item click
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// Enable ActionBar app icon to behave as action to toggle nav drawer
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				// TODO Auto-generated method stub
				jLog.d("onDrawerClosed");

				super.onDrawerClosed(view);
			}

			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				// Set the title on the action when drawer open
				jLog.d("onDrawerOpened");
				getSupportActionBar().setTitle(mDrawerTitle);
				super.onDrawerOpened(drawerView);
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}

		if (jLog.isConnect) {
			String str = String.format("jLog is connected : %s : %s", ip, port);
			Log.d(TAG, str);
			jLog.d(str);
		}

	}

	SearchView mSearchView;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Used to put dark icons on light action bar boolean isLight =
		boolean isLight = SampleList.THEME == R.style.Theme_Sherlock_Light;

		// Create the search view SearchView mSearchView = new
		mSearchView = new SearchView(getSupportActionBar().getThemedContext());
		mSearchView.setQueryHint("Search from Youtube");

		mSearchView.setOnQueryTextListener((OnQueryTextListener) this);
		mSearchView.setOnSuggestionListener((OnSuggestionListener) this);

		if (mSuggestionsAdapter == null) {
			MatrixCursor cursor = new MatrixCursor(COLUMNS);
			cursor.addRow(new String[] { "1", "Let It Go" });
			cursor.addRow(new String[] { "2", "steve jobs" });
			cursor.addRow(new String[] { "3", "TED" });
			mSuggestionsAdapter = new SuggestionsAdapter(getSupportActionBar()
					.getThemedContext(), cursor);
		}

		mSearchView.setSuggestionsAdapter(mSuggestionsAdapter);

		menu.add("Search")
				.setIcon(
						isLight ? R.drawable.ic_search_inverse
								: R.drawable.abs__ic_search)
				.setActionView(mSearchView)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_IF_ROOM
								| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {

			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				jLog.d("closeDrawer");
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				jLog.d("openDrawer");
				mDrawerLayout.openDrawer(mDrawerList);
			}
		}

		return super.onOptionsItemSelected(item);
	}

	// ListView click listener in the navigation drawer
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// Locate Position
		switch (position) {
		case 0:
			ft.replace(R.id.content_frame, fragment_home);
			break;
		case 1:
			ft.replace(R.id.content_frame, fragment_myfile);
			break;
		default:
			ft.replace(R.id.content_frame, fragment_home);
			break;
		}
		ft.commit();
		mDrawerList.setItemChecked(position, true);
		// Get the title followed by the position
		setTitle(title[position]);
		// Close drawer
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	@Override
	public void onBackPressed() {

		FragmentManager manager = getSupportFragmentManager();
		if (manager.getBackStackEntryCount() > 0) {
			// If there are back-stack entries, leave the FragmentActivity
			// implementation take care of them.
			manager.popBackStack();

		} else {
			// Otherwise, ask user if he wants to leave :)
			super.onBackPressed();
		}
	}

	@Override
	public boolean onSuggestionSelect(int position) {
		// TODO Auto-generated method stub
		jLog.d("onSuggestionSelect = " + position);
		return false;
	}

	@Override
	public boolean onSuggestionClick(int position) {
		// TODO Auto-generated method stub

		Cursor c = (Cursor) mSuggestionsAdapter.getItem(position);
		String query = c.getString(c
				.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
		jLog.d("onSuggestionClick = " + query);
		mSearchView.setQuery(query, false);

		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		jLog.d("onQueryTextSubmit = " + query);
		
		
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		jLog.d("onQueryTextChange = " + newText);
		return false;
	}

	private class SuggestionsAdapter extends CursorAdapter {

		public SuggestionsAdapter(Context context, Cursor c) {
			super(context, c, 0);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(context);
			View v = inflater.inflate(android.R.layout.simple_list_item_1,
					parent, false);
			return v;
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			TextView tv = (TextView) view;
			final int textIndex = cursor
					.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1);
			tv.setText(cursor.getString(textIndex));
		}
	}

}
