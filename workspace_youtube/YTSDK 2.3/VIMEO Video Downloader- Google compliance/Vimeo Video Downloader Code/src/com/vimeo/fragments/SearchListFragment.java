package com.vimeo.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vimeo.sdk.access.VideoItem;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vimeo.activities.VimeoSDKUtils;
import com.vimeo.activities.adapters.SearchListAdapter;
import com.vimeo.common.utils.ConnectionChecker;
import com.vimeo.common.utils.MyLog;
import com.vimeo.common.utils.Utils;
import com.vimeo.testapp.R;

public class SearchListFragment extends Fragment implements OnScrollListener {

	private static final String KEY_CONTENT = "ItemListFragment:Items";
	private static final String KEY_FILE_NAME = "ItemListFragment:fileName";

	private GridView mGridView;

	LinearLayout progressBar;
	SearchListAdapter mListAdapter;
	private List<VideoItem> item = new ArrayList<VideoItem>();

	private static SearchListFragment thisPointer;
	private int position;

	private EditText mEditText;
	private LinearLayout mSearchButton;

	private int currentPage = 1;
	boolean loadingMore = false;
	private String searchText;

	public static SearchListFragment getInstance(int position) {

		// if (gridFragent == null) {
		thisPointer = new SearchListFragment();
		// }
		Bundle args = new Bundle();
		args.putInt("position", position);
		thisPointer.setArguments(args);
		return thisPointer;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		restoreData(savedInstanceState);
		Bundle bundle = this.getArguments();
		if (bundle != null) {
			this.position = bundle.getInt("position");
		}
		super.onCreate(savedInstanceState);
		Utils.loadFullScreenAd(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_search_video, container,
				false);

		progressBar = (LinearLayout) view.findViewById(R.id.loadingPanel);
		mGridView = (GridView) view.findViewById(R.id.list);
		mGridView.setOnScrollListener(this);

		mEditText = (EditText) view.findViewById(R.id.edit_seach);
		mEditText
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEARCH) {
							performSearch();
							return true;
						}
						return false;
					}

				});

		mSearchButton = (LinearLayout) view
				.findViewById(R.id.searchButtonLayout);
		mSearchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				performSearch();

			}
		});

		return view;
	}

	private void performSearch() {

		Utils.showFullScreenAd(getActivity());

		searchText = mEditText.getText().toString();
		if (searchText == null || searchText.length() == 0) {
			return;
		}

		closeKeyboard();

		if (!isConnectivityPresent()) {
			return;
		}

		Pattern pattern = Pattern.compile("\\s+");
		Matcher matcher = pattern.matcher(searchText);
		searchText = matcher.replaceAll("+");

		new GetVideoListFromYouTube().execute(searchText);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onPause() {
		super.onPause();

		closeKeyboard();
	}

	private void closeKeyboard() {

		if (SearchListFragment.this == null) {
			return;
		}

		try {
			InputMethodManager imm = (InputMethodManager) getActivity()
					.getSystemService(getActivity().INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void onListItemClick(GridView g, View v, int position, long id) {
		VimeoSDKUtils.getYTSDK().showCustomDialog(getActivity(), true, false);
		VimeoSDKUtils.getYTSDK().download(getActivity(),
				item.get(position).getVideoId());
	}

	private void restoreData(Bundle savedInstanceState) {
		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_CONTENT)) {
			item = (ArrayList<VideoItem>) savedInstanceState.get(KEY_CONTENT);
			position = savedInstanceState.getInt(KEY_FILE_NAME);
		}
	}

	private class GetVideoListFromYouTube extends
			AsyncTask<String, Void, List<VideoItem>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressBar.setVisibility(View.VISIBLE);
			mGridView.setVisibility(View.GONE);
		}

		@Override
		protected List<VideoItem> doInBackground(String... params) {
			List<VideoItem> videoList = VimeoSDKUtils.getYTSDK().getVideosList(
					getActivity(), params[0], currentPage++);

			if (videoList == null) {
				videoList = new ArrayList<VideoItem>();
			}

			return videoList;
		}

		@Override
		protected void onPostExecute(List<VideoItem> result) {
			super.onPostExecute(result);
			// Cancel the Loading Dialog
			progressBar.setVisibility(View.GONE);
			addVideoList(result);
			loadingMore = false;
		}

	}

	private void addVideoList(List<VideoItem> videoList) {

		if (videoList == null || videoList.size() == 0) {

			if (!progressBar.isShown()) {

			}
			return;
		}

		if (item != null && item.isEmpty()) {
			item = videoList;
			mListAdapter = new SearchListAdapter(getActivity(), item, position,
					R.layout.grid_item);
			if (mGridView != null) {
				mGridView.setAdapter(mListAdapter);
			}

			mGridView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView parent, View view,
						int position, long id) {
					onListItemClick((GridView) parent, view, position, id);
				}
			});

		} else {
			item.addAll(videoList);
			mListAdapter.notifyDataSetChanged();
		}

		// This has to run only at fist time from next the on scroll down
		// will
		// run
		if (!item.isEmpty()) {
			mGridView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
		}

	}

	private boolean isConnectivityPresent() {
		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		ConnectionChecker connectionChecker = new ConnectionChecker(
				getActivity(), cm, getActivity());

		if (MyLog.disableConnectionCheckForDebug) {
			return true;
		}

		if (connectionChecker.isOnline()) {
			return true;
		} else {
			showConnectivityErrorDialog();
			return false;
		}
	}

	private void showConnectivityErrorDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setCancelable(true);
		builder.setIcon(null);
		builder.setTitle(null);
		builder.setMessage(getActivity().getString(R.string.enablewifiMsg));
		builder.setInverseBackgroundForced(true);
		builder.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent settingPage = new Intent(
								android.provider.Settings.ACTION_SETTINGS);
						getActivity().startActivityForResult(settingPage, 0);
						dialog.dismiss();
					}
				});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		int totalItems = firstVisibleItem + visibleItemCount;
		if (item == null || item.isEmpty()) {
			return;
		}
		// is the bottom item visible & not loading more already ? Load more !
		if ((totalItems == totalItemCount) && !(loadingMore)) {
			loadingMore = true;
			new GetVideoListFromYouTube().execute(searchText);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

}