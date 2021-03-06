package com.wecandoit.jinju_0_0_4;

import com.wecandoit.jinju_0_0_4.*;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class jViewPagerAdapter_home extends FragmentPagerAdapter {

	// Declare the number of ViewPager pages
	private String titles[] = new String[] { 
			"Recent", 
			"Popular",
			"Favorite",
			//"Search",
			};

	public jViewPagerAdapter_home(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return new FragmentTab_Recent();
		case 1:
			return  new FragmentTab_Popular();
		case 2:
			return new FragmentTab_Favorite();
		case 3:
			return new FragmentTab_Search();
		}
		return null;
	}

	public CharSequence getPageTitle(int position) {
		return titles[position];
	}

	@Override
	public int getCount() {
		return titles.length;
	}

}