package com.example.googlesto.adapter;

import java.util.List;

import com.example.googlesto.Detail_activity;
import com.example.googlesto.domin.AppInfo;
import com.example.googlesto.holder.BaseHoder;
import com.example.googlesto.holder.ListBaseHolder;
import com.example.googlesto.tools.UIUtils;

import android.content.Intent;
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

	@Override
	public void onInnerItemClick(int position) {
		super.onInnerItemClick(position);
		AppInfo appInfo = datas.get(position);
		Intent intent = new Intent(UIUtils.getContext(),
				Detail_activity.class);
		intent.putExtra("packageName", appInfo.getPackageName());
		UIUtils.startActivity(intent);
	}

}
