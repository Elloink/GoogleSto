package com.example.googlesto.holder;

import com.example.googlesto.R;
import com.example.googlesto.adapter.DefaultAdapter;
import com.example.googlesto.tools.UIUtils;

import android.view.View;
import android.widget.RelativeLayout;

public class MoreHoder extends BaseHoder<Integer> {
	public static final int HAS_NO_MORE = 0; // 没有额外数据了
	public static final int LOAD_ERROR = 1;// 加载失败
	public static final int HAS_MORE = 2;// 有额外数据
	private boolean  hasmore;
	private RelativeLayout rl_more_loading,rl_more_error;
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.load_more);
		rl_more_loading=(RelativeLayout) view.findViewById(R.id.rl_more_loading);
		rl_more_error=(RelativeLayout) view.findViewById(R.id.rl_more_error);
		return view;
	}

	private DefaultAdapter adapter;

	public MoreHoder(DefaultAdapter adapter, boolean hasmore) {
		super();
		this.adapter = adapter;
		this.hasmore = hasmore;
		if(!hasmore){
			setDatas(0);//如果没有更多设置界面不可见
		}
	}

	// 当moreholder调用此方法时吗，应该加载更多数据
	@Override
	public View getContentview() {
		if (hasmore) {
			loadmore();//为true加载更多，默认true
		}
		
		return super.getContentview();
	}

	/**
	 * 请求服务器，加载下一批数据 把数据交给adapter加载
	 */
	private void loadmore() {
		adapter.loadMore();
	}

	/**
	 * 根据数据做界面的修改
	 */
	@Override
	public void refreshView(Integer datas) {
		rl_more_error.setVisibility(datas==LOAD_ERROR?View.VISIBLE:View.GONE);
		rl_more_loading.setVisibility(datas==HAS_MORE?View.VISIBLE:View.GONE);
	}

}
