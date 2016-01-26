package com.example.googlesto.adapter;

import java.util.List;

import com.example.googlesto.domin.AppInfo;
import com.example.googlesto.holder.BaseHoder;
import com.example.googlesto.holder.ListBaseHolder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class ListBaseAdapter extends DefaultAdapter<AppInfo> {

	public ListBaseAdapter(List<AppInfo> datas, ListView list) {
		super(datas,list);
	}

	@Override
	public BaseHoder<AppInfo> getHolder() {
		return new ListBaseHolder();
	}

	@Override
	public abstract List<AppInfo> onload() ;



}
