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
	// ���ص�����״̬
	public static final int STATE_UNKOWN = 0;
	public static final int STATE_LOADING = 1;
	public static final int STATE_ERROR = 2;
	public static final int STATE_EMPTY = 3;
	public static final int STATE_SUCCESS = 4;
	public static int state = STATE_UNKOWN;// ��ǰ��״̬

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (frameLayout == null) {// framelayout���ڿյ�ʱ�򴴽�
			frameLayout = new FrameLayout(getContext());
			init();// ��framelayout������ֲ�ͬ��״̬
		}else{
			com.example.googlesto.tools.ViewUtils.removeParent(frameLayout);//�Ƴ�framelayout֮ǰ�ĵ�
		}

		showpage();// ���ݲ�ͬ��״̬��ʾ��ͬ�Ľ���
		show();// ���ݷ����������ݣ��л�״̬
		
		return frameLayout;
	}

	// ���ֽ���
	private View loadingView;// �����еĽ���
	private View errorView;// �������
	private View emptyView;// �ս���
	private View successView;// ���سɹ��Ľ���
	FrameLayout frameLayout;

	/**
	 * ��framelayout������ֲ�ͬ��״̬
	 */
	private void init() {
		loadingView = creatrLoadingView();// �����˼��ؽ���
		if (loadingView != null) {//
			frameLayout.addView(loadingView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		errorView = createErrorView(); // ���ش������
		if (errorView != null) {
			frameLayout.addView(errorView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		emptyView = createEmptyView(); // ���ؿյĽ���
		if (emptyView != null) {
			frameLayout.addView(emptyView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		// showpage();//���ݲ�ͬ��״̬��ʾ��ͬ��״̬
	}

	/**
	 * ���ݲ�ͬ��״̬��ʾ��ͬ�Ľ���
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

	// �����ɹ�����
	private View createSuccessView() {
		TextView tv = new TextView(getActivity());
		tv.setText("���سɹ���....");
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
	 * ���ݷ����������ݣ��л�״̬
	 */
	private void show() {
		if (state == STATE_EMPTY || state == STATE_ERROR) {
			state = STATE_LOADING;
		}
		// �����������ȡ�������ϵ����ݽ����ж�
		// �й����ӷ������Ĳ���ֻ�������߳�ִ�У���������̣߳�,���������߳��в����л�����,����ִ��showpage()����ֻ����runuiThread������ִ��

		new Thread() {
			public void run() {
				SystemClock.sleep(2000);
				// �������������һ�����
				final LoadResult result = load();
				if (getActivity() != null) {

					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							if (result != null) {
								state = 2 + new Random().nextInt(3);
								// state = result.getValue();// ��ȡ״ֵ̬
								// ״̬�ı䣬�����жϵ�ǰӦ����ʾ�Ľ���
								showpage();
							}
						}
					});
				}

			};
		}.start();
		showpage();// �����߳���ִ��
	}

	private LoadResult load() {
		return LoadResult.success;
	}

	/**
	 * �����ս���
	 * 
	 * @return
	 */
	private View createEmptyView() {
		View view = View.inflate(getActivity(), R.layout.loadpage_empty, null);
		return view;
	}

	/**
	 * �����������
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
	 * ���������н���
	 * 
	 * @return
	 */
	private View creatrLoadingView() {
		View view = View
				.inflate(getActivity(), R.layout.loadpage_loading, null);
		return view;
	}

}
