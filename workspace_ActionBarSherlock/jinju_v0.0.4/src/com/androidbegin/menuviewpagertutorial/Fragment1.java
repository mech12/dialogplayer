package com.androidbegin.menuviewpagertutorial;
 
import com.actionbarsherlock.app.SherlockFragment;
import com.wecandoit.jinju_0_0_3.R;
 
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 
public class Fragment1 extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment1, container, false);
		return rootView;
	}
 
}