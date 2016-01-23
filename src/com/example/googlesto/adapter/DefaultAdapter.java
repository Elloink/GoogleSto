package com.example.googlesto.adapter;

import java.util.List;

import com.example.googlesto.domin.AppInfo;
import com.example.googlesto.holder.BaseHoder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class DefaultAdapter<Data> extends BaseAdapter {
	protected List<Data> datas;
	public DefaultAdapter(List<Data> datas) {
		this.datas = datas;
	}
	
	public List<Data> getDatas() {
		return datas;
	}

	public void setDatas(List<Data> datas) {
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup viewgoup) {
		BaseHoder<Data> holder;
		if (convertview == null) {
			holder = getHolder();
		} else {
			holder = (BaseHoder<Data>) convertview.getTag();
		}
		Data info = datas.get(position);
		holder.setDatas(info);
		return holder.getContentview();
	}

	public abstract BaseHoder<Data> getHolder();

}
