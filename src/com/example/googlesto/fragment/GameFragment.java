package com.example.googlesto.fragment;


import java.util.List;

import com.example.googlesto.R;
import com.example.googlesto.adapter.ListBaseAdapter;
import com.example.googlesto.domin.AppInfo;
import com.example.googlesto.protocol.AppProtocol;
import com.example.googlesto.protocol.GameProtocol;
import com.example.googlesto.view.BaseListView;
import com.example.googlesto.view.LoadingPage.LoadResult;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GameFragment extends BaseFragment {
	private List<AppInfo> datas;

	@Override
	public View createSuccessView() {
		BaseListView listview = new BaseListView(getContext());
		bitmapUtils = new BitmapUtils(getContext());
		listview.setAdapter(new ListBaseAdapter(datas,listview){

			@Override
			public List<AppInfo> onload() {
				GameProtocol hProtocol = new GameProtocol();
				List<AppInfo>	load = hProtocol.load(datas.size());
				datas.addAll(load);
				return load;
			}
			
		});
		listview.setOnScrollListener(new PauseOnScrollListener(bitmapUtils,
				false, true));
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default); // 设置如果图片加载中显示的图片
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);// 加载失败显示的图片
		return listview;
	}

	@Override
	public LoadResult load() {
		GameProtocol hProtocol = new GameProtocol();
		datas = hProtocol.load(0);
		return checkData(datas);
	}

}
