package com.example.googlesto.view;

import com.example.googlesto.R;
import com.example.googlesto.manager.ThreadManager;
import com.example.googlesto.tools.UIUtils;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

/**
 * ����һ���Զ����֡���֣��������԰�BaseFragment�Ĵ����ȡ������
 * 
 * @author threes
 *
 */
public abstract class LoadingPage extends FrameLayout {
	// ���ص�����״̬
		public static final int STATE_UNKOWN = 0;
		public static final int STATE_LOADING = 1;
		public static final int STATE_ERROR = 2;
		public static final int STATE_EMPTY = 3;
		public static final int STATE_SUCCESS = 4;
		public  int state = STATE_UNKOWN;// ��ǰ��״̬

		// ���ֽ���
		private View loadingView;// �����еĽ���
		private View errorView;// �������
		private View emptyView;// �ս���
		private View successView;// ���سɹ��Ľ���
	

	public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	public LoadingPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LoadingPage(Context context) {
		super(context);
		init();
	}
	/**
	 * ���ؽ����ö����
	 * 
	 * @author threes
	 *
	 */
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
	 * ��framelayout������ֲ�ͬ��״̬
	 */
	private void init() {
		loadingView = creatrLoadingView();// �����˼��ؽ���
		if (loadingView != null) {//
			this.addView(loadingView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		errorView = createErrorView(); // ���ش������
		if (errorView != null) {
			this.addView(errorView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		emptyView = createEmptyView(); // ���ؿյĽ���
		if (emptyView != null) {
			this.addView(emptyView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		showpage();// ���ݲ�ͬ��״̬��ʾ��ͬ�Ľ���
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
				this.addView(successView, new FrameLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				successView.setVisibility(View.VISIBLE);
			}
		}else{
			if (successView!=null) {
				successView.setVisibility(View.INVISIBLE);
			}
		}
	}
	/**
	 * ���ݷ����������ݣ��л�״̬
	 */
	public void show() {
		if (state == STATE_EMPTY || state == STATE_ERROR) {
			state = STATE_LOADING;
		}
		// �����������ȡ�������ϵ����ݽ����ж�
		// �й����ӷ������Ĳ���ֻ�������߳�ִ�У���������̣߳�,���������߳��в����л�����,����ִ��showpage()����ֻ����runuiThread������ִ��
		ThreadManager.getInstance().createLongPool().excute(new Runnable() {
			@Override
			public void run() {
				SystemClock.sleep(2000);
				// �������������һ�����
				final LoadResult result = load();
					UIUtils.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (result != null) {
								 state = result.getValue();// ��ȡ״ֵ̬
								// ״̬�ı䣬�����жϵ�ǰӦ����ʾ�Ľ���
								showpage();
							}
						}
					});
			}
		});
		showpage();// �����߳���ִ��
	}

	/**
	 * �����ɹ�����
	 * 
	 * @return
	 */
	public abstract View createSuccessView();

	/**
	 * ��������
	 * 
	 * @return ���صĽ��
	 */
	public abstract LoadResult load();
	/**
	 * �����ս���
	 * 
	 * @return
	 */
	private View createEmptyView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.loadpage_empty, null);
		return view;
	}

	/**
	 * �����������
	 * 
	 * @return
	 */
	private View createErrorView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.loadpage_error, null);
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
				.inflate(UIUtils.getContext(), R.layout.loadpage_loading, null);
		return view;
	}
	

}
