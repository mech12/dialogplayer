package com.wecandoit.jinju_0_0_4;

import com.actionbarsherlock.app.SherlockFragment;
import com.wecandoit.jinju_0_0_4.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentTab_Search extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search, container, false);
		return rootView;
	}
	
}
