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
 * 创建一个自定义的帧布局，这样可以把BaseFragment的代码抽取到这里
 * 
 * @author threes
 *
 */
public abstract class LoadingPage extends FrameLayout {
	// 加载的五种状态
		public static final int STATE_UNKOWN = 0;
		public static final int STATE_LOADING = 1;
		public static final int STATE_ERROR = 2;
		public static final int STATE_EMPTY = 3;
		public static final int STATE_SUCCESS = 4;
		public  int state = STATE_UNKOWN;// 当前的状态

		// 四种界面
		private View loadingView;// 加载中的界面
		private View errorView;// 错误界面
		private View emptyView;// 空界面
		private View successView;// 加载成功的界面
	

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
	 * 加载结果的枚举类
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
	 * 在framelayout添加四种不同的状态
	 */
	private void init() {
		loadingView = creatrLoadingView();// 创建了加载界面
		if (loadingView != null) {//
			this.addView(loadingView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		errorView = createErrorView(); // 加载错误界面
		if (errorView != null) {
			this.addView(errorView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		emptyView = createEmptyView(); // 加载空的界面
		if (emptyView != null) {
			this.addView(emptyView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		showpage();// 根据不同的状态显示不同的界面
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
	 * 根据服务器的数据，切换状态
	 */
	public void show() {
		if (state == STATE_EMPTY || state == STATE_ERROR) {
			state = STATE_LOADING;
		}
		// 请求服务器获取服务器上的数据进行判断
		// 有关连接服务器的操作只能在子线程执行（所以添加线程）,但是在子线程中不能切换界面,所以执行showpage()方法只能在runuiThread方法中执行
		ThreadManager.getInstance().createLongPool().excute(new Runnable() {
			@Override
			public void run() {
				SystemClock.sleep(2000);
				// 请求服务器返回一个结果
				final LoadResult result = load();
					UIUtils.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (result != null) {
								 state = result.getValue();// 获取状态值
								// 状态改变，从新判断当前应该显示的界面
								showpage();
							}
						}
					});
			}
		});
		showpage();// 在主线程中执行
	}

	/**
	 * 创建成功界面
	 * 
	 * @return
	 */
	public abstract View createSuccessView();

	/**
	 * 加载数据
	 * 
	 * @return 加载的结果
	 */
	public abstract LoadResult load();
	/**
	 * 创建空界面
	 * 
	 * @return
	 */
	private View createEmptyView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.loadpage_empty, null);
		return view;
	}

	/**
	 * 创建错误界面
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
	 * 创建加载中界面
	 * 
	 * @return
	 */
	private View creatrLoadingView() {
		View view = View
				.inflate(UIUtils.getContext(), R.layout.loadpage_loading, null);
		return view;
	}
	

}
