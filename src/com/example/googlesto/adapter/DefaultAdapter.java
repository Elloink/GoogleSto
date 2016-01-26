package com.example.googlesto.adapter;

import java.util.List;

import javax.crypto.spec.PSource;

import com.example.googlesto.domin.AppInfo;
import com.example.googlesto.holder.BaseHoder;
import com.example.googlesto.holder.MoreHoder;
import com.example.googlesto.manager.ThreadManager;
import com.example.googlesto.tools.UIUtils;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public abstract class DefaultAdapter<Data> extends BaseAdapter implements
		OnItemClickListener {
	protected List<Data> datas;
	private static final int DEFAULT_ITEM = 0;// 默认类型
	private static final int MORE_ITEM = 1;// 更多条目
	ListView list;

	public DefaultAdapter(List<Data> datas, ListView list) {
		this.datas = datas;
		list.setOnItemClickListener(this);
		this.list = list;
	}

	/**
	 * listview 点击回调的方法
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		position = position - list.getHeaderViewsCount();// 获取到顶部条目的数量
		onInnerItemClick(position);
	}

	public List<Data> getDatas() {
		return datas;
	}

	public void setDatas(List<Data> datas) {
		this.datas = datas;
	}

	// 在该方法处理条目的点击事件
	public void onInnerItemClick(int position) {

	}

	@Override
	public int getCount() {
		return datas.size() + 1;// 最后面一个条目，加载更多
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// 根据位置判断当前条目是什么类型
	@Override
	public int getItemViewType(int position) {
		if (position == datas.size()) {// 最后一个条目
			return MORE_ITEM;
		}
		return getDefaultItemViewType(position);// 如果不是最后一个条目返回一个默认的类型
	}

	private int getDefaultItemViewType(int position) {
		return DEFAULT_ITEM;
	}

	// 当前listview有几种条目类型
	@Override
	public int getViewTypeCount() {
		return super.getViewTypeCount() + 1;// 当前两种类型
	}

	@Override
	public View getView(int position, View convertview, ViewGroup viewgoup) {
		BaseHoder holder = null;
		// 第一种写法
		// if (convertview == null) {
		// if (getItemViewType(position) == MORE_ITEM) {
		// holder = getMoreHolder();
		// } else {
		// holder = getHolder();// 普通条目
		// }
		// } else {
		// if (getItemViewType(position) == DEFAULT_ITEM) {
		// holder = (BaseHoder<Data>) convertview.getTag();
		// } else {
		// holder = getMoreHolder();
		// }
		// }
		// if (getItemViewType(position) == DEFAULT_ITEM) {
		// Data info = datas.get(position);
		// holder.setDatas(info);
		// }
		// 第二种写法
		switch (getItemViewType(position)) { // 判断当前条目时什么类型
		case MORE_ITEM:
			if (convertview == null) {
				holder = getMoreHolder();
			} else {
				holder = (BaseHoder<Data>) convertview.getTag();
			}
			break;
		case DEFAULT_ITEM:
			if (convertview == null) {
				holder = getHolder();
			} else {
				holder = (BaseHoder<Data>) convertview.getTag();
			}
			if (position < datas.size()) {
				holder.setDatas(datas.get(position));
			}
			break;
		}
		return holder.getContentview(); // 如果当前Holder 恰好是MoreHolder
										// 证明MoreHOlder已经显示
	}

	// 此处的目的是为了传递个moreholder,以此可以给后面加载数据设置图像
	private MoreHoder holder;

	private BaseHoder getMoreHolder() {
		if (holder != null) {
			return holder;
		} else {
			holder = new MoreHoder(this);
			return holder;
		}

	}

	public abstract BaseHoder<Data> getHolder();

	/**
	 * 当加载更多条目显示的时候调用该方法
	 */
	public void loadMore() {
		ThreadManager.getInstance().createLongPool().excute(new Runnable() {
			// 在子线成中加载更多
			@Override
			public void run() {
				final List<Data> newData = onload();
				UIUtils.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (newData == null) {
							holder.setDatas(MoreHoder.LOAD_ERROR);
						} else if (newData.size() == 0) {
							// 服务器没有数据
							holder.setDatas(MoreHoder.HAS_NO_MORE);
						} else {
							// 成功了
							holder.setDatas(MoreHoder.HAS_MORE);
							datas.addAll(newData);
							notifyDataSetChanged();// 在主线程刷新界面
						}

					}
				});

			}

		});
	}

	/**
	 * 加载更多数据
	 */
	public abstract List<Data> onload();
}
