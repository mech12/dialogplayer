package info.androidhive.tabsswipe.adapter;

import info.androidhive.tabsswipe.*;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new TopRatedFragment();
		case 1:
			// Games fragment activity
			return new GamesFragment();
		case 2:
			// Movies fragment activity
			return new MoviesFragment();
		case 3:
			// Top Rated fragment activity
			return new TopRatedFragment();
		case 4:
			// Games fragment activity
			return new GamesFragment();
		case 5:
			// Top Rated fragment activity
			return new TopRatedFragment();
		case 6:
			// Games fragment activity
			return new GamesFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 6;
	}

}
