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
	private static final int DEFAULT_ITEM = 0;// Ĭ������
	private static final int MORE_ITEM = 1;// ������Ŀ
	ListView list;

	public DefaultAdapter(List<Data> datas, ListView list) {
		this.datas = datas;
		list.setOnItemClickListener(this);
		this.list = list;
	}

	/**
	 * listview ����ص��ķ���
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		position = position - list.getHeaderViewsCount();// ��ȡ��������Ŀ������
		onInnerItemClick(position);
	}

	public List<Data> getDatas() {
		return datas;
	}

	public void setDatas(List<Data> datas) {
		this.datas = datas;
	}

	// �ڸ÷���������Ŀ�ĵ���¼�
	public void onInnerItemClick(int position) {

	}

	@Override
	public int getCount() {
		return datas.size() + 1;// �����һ����Ŀ�����ظ���
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// ����λ���жϵ�ǰ��Ŀ��ʲô����
	@Override
	public int getItemViewType(int position) {
		if (position == datas.size()) {// ���һ����Ŀ
			return MORE_ITEM;
		}
		return getDefaultItemViewType(position);// ����������һ����Ŀ����һ��Ĭ�ϵ�����
	}

	private int getDefaultItemViewType(int position) {
		return DEFAULT_ITEM;
	}

	// ��ǰlistview�м�����Ŀ����
	@Override
	public int getViewTypeCount() {
		return super.getViewTypeCount() + 1;// ��ǰ��������
	}

	@Override
	public View getView(int position, View convertview, ViewGroup viewgoup) {
		BaseHoder holder = null;
		// ��һ��д��
		// if (convertview == null) {
		// if (getItemViewType(position) == MORE_ITEM) {
		// holder = getMoreHolder();
		// } else {
		// holder = getHolder();// ��ͨ��Ŀ
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
		// �ڶ���д��
		switch (getItemViewType(position)) { // �жϵ�ǰ��Ŀʱʲô����
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
		return holder.getContentview(); // �����ǰHolder ǡ����MoreHolder
										// ֤��MoreHOlder�Ѿ���ʾ
	}

	// �˴���Ŀ����Ϊ�˴��ݸ�moreholder,�Դ˿��Ը����������������ͼ��
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
	 * �����ظ�����Ŀ��ʾ��ʱ����ø÷���
	 */
	public void loadMore() {
		ThreadManager.getInstance().createLongPool().excute(new Runnable() {
			// �����߳��м��ظ���
			@Override
			public void run() {
				final List<Data> newData = onload();
				UIUtils.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (newData == null) {
							holder.setDatas(MoreHoder.LOAD_ERROR);
						} else if (newData.size() == 0) {
							// ������û������
							holder.setDatas(MoreHoder.HAS_NO_MORE);
						} else {
							// �ɹ���
							holder.setDatas(MoreHoder.HAS_MORE);
							datas.addAll(newData);
							notifyDataSetChanged();// �����߳�ˢ�½���
						}

					}
				});

			}

		});
	}

	/**
	 * ���ظ�������
	 */
	public abstract List<Data> onload();
}
