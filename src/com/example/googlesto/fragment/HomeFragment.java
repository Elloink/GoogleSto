package com.example.googlesto.fragment;

import java.util.Random;

import com.example.googlesto.R;

//import android.R;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

public class HomeFragment extends Fragment {
	// 加载的五种状态
	public static final int STATE_UNKOWN = 0;
	public static final int STATE_LOADING = 1;
	public static final int STATE_ERROR = 2;
	public static final int STATE_EMPTY = 3;
	public static final int STATE_SUCCESS = 4;
	public static int state = STATE_UNKOWN;// 当前的状态

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (frameLayout == null) {// framelayout等于空的时候创建
			frameLayout = new FrameLayout(getContext());
			init();// 在framelayout添加四种不同的状态
		}else{
			com.example.googlesto.tools.ViewUtils.removeParent(frameLayout);//移除framelayout之前的爹
		}

		showpage();// 根据不同的状态显示不同的界面
		show();// 根据服务器的数据，切换状态
		
		return frameLayout;
	}

	// 四种界面
	private View loadingView;// 加载中的界面
	private View errorView;// 错误界面
	private View emptyView;// 空界面
	private View successView;// 加载成功的界面
	FrameLayout frameLayout;

	/**
	 * 在framelayout添加四种不同的状态
	 */
	private void init() {
		loadingView = creatrLoadingView();// 创建了加载界面
		if (loadingView != null) {//
			frameLayout.addView(loadingView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		errorView = createErrorView(); // 加载错误界面
		if (errorView != null) {
			frameLayout.addView(errorView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		emptyView = createEmptyView(); // 加载空的界面
		if (emptyView != null) {
			frameLayout.addView(emptyView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		// showpage();//根据不同的状态显示不同的状态
	}

	/**
	 * 根据不同的状态显示不同的界面
	 */
	private void showpage() {
		if (loadingView != null) {
			loadingView.setVisibility(state == STATE_UNKOWN
					|| state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
		}
		if (errorView != null) {
			errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE
					: View.INVISIBLE);
		}
		if (emptyView != null) {
			emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE
					: View.INVISIBLE);
		}
		if (state == STATE_SUCCESS) {
			successView = createSuccessView();
			if (successView != null) {
				frameLayout.addView(successView, new FrameLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				successView.setVisibility(View.VISIBLE);
			}

		}
	}

	// 创建成功界面
	private View createSuccessView() {
		TextView tv = new TextView(getActivity());
		tv.setText("加载成功了....");
		tv.setTextSize(30);
		return tv;
	}

	public enum LoadResult {
		error(2), empty(3), success(4);

		int value;

		LoadResult(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}

	/**
	 * 根据服务器的数据，切换状态
	 */
	private void show() {
		if (state == STATE_EMPTY || state == STATE_ERROR) {
			state = STATE_LOADING;
		}
		// 请求服务器获取服务器上的数据进行判断
		// 有关连接服务器的操作只能在子线程执行（所以添加线程）,但是在子线程中不能切换界面,所以执行showpage()方法只能在runuiThread方法中执行

		new Thread() {
			public void run() {
				SystemClock.sleep(2000);
				// 请求服务器返回一个结果
				final LoadResult result = load();
				if (getActivity() != null) {

					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							if (result != null) {
								state = 2 + new Random().nextInt(3);
								// state = result.getValue();// 获取状态值
								// 状态改变，从新判断当前应该显示的界面
								showpage();
							}
						}
					});
				}

			};
		}.start();
		showpage();// 在主线程中执行
	}

	private LoadResult load() {
		return LoadResult.success;
	}

	/**
	 * 创建空界面
	 * 
	 * @return
	 */
	private View createEmptyView() {
		View view = View.inflate(getActivity(), R.layout.loadpage_empty, null);
		return view;
	}

	/**
	 * 创建错误界面
	 * 
	 * @return
	 */
	private View createErrorView() {
		View view = View.inflate(getActivity(), R.layout.loadpage_error, null);
		Button bt = (Button) view.findViewById(R.id.page_bt);
		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				show();
			}
		});
		return view;
	}

	/**
	 * 创建加载中界面
	 * 
	 * @return
	 */
	private View creatrLoadingView() {
		View view = View
				.inflate(getActivity(), R.layout.loadpage_loading, null);
		return view;
	}

}
