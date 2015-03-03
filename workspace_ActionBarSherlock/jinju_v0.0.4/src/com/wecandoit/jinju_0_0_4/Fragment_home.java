package com.wecandoit.jinju_0_0_4;

import java.lang.reflect.Field;
import com.actionbarsherlock.app.SherlockFragment;
import com.wecandoit.jinju_0_0_4.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.*;

public class Fragment_home extends SherlockFragment 
{
	//private ActionBar actionBar ;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


		View view = inflater.inflate(R.layout.viewpager_main_home, container, false);
		// Locate the ViewPager in viewpager_main.xml
		ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
		// Set the jViewPagerAdapter_home into ViewPager
		mViewPager.setAdapter(new jViewPagerAdapter_home(getChildFragmentManager()));
		mViewPager.setCurrentItem(1);
		
		//ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
		
				
		return view;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class
					.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}