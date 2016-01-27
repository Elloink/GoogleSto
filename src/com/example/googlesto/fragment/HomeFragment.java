package com.example.googlesto.fragment;

import java.util.List;
import java.util.Random;

import com.example.googlesto.Detail_activity;
import com.example.googlesto.R;
import com.example.googlesto.adapter.DefaultAdapter;
import com.example.googlesto.adapter.ListBaseAdapter;
import com.example.googlesto.domin.AppInfo;
import com.example.googlesto.globle.GlobalContants;
import com.example.googlesto.holder.BaseHoder;
import com.example.googlesto.holder.HomePictureHolder;
import com.example.googlesto.holder.ListBaseHolder;
import com.example.googlesto.protocol.HomeProtocol;
import com.example.googlesto.tools.BitmapHelper;
import com.example.googlesto.tools.UIUtils;
import com.example.googlesto.view.BaseListView;
import com.example.googlesto.view.LoadingPage.LoadResult;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;

import android.content.Intent;
import android.graphics.drawable.Drawable;
//import android.R;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ViewUtils;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends BaseFragment {
	private List<AppInfo> datas;
	private List<String> pictures;// 轮播图片集合

	// 当此fragment挂载的activity创建的时候调用show（）；因为刚加载的时候，fragment没有滑动，所以不能调用show()
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		show();
	}

	// 创建成功界面
	public View createSuccessView() {
		BaseListView listview = new BaseListView(getContext());
		HomePictureHolder hpHolder = new HomePictureHolder();
		hpHolder.setDatas(pictures);
		View hpview = hpHolder.getContentview();// 得到homepictureholder管理的对象
		// hpview.setLayoutParams(new
		// AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		listview.addHeaderView(hpview);// // 把hpholder里的view对象 添加到listView的上面

		bitmapUtils = new BitmapUtils(getContext());
		listview.setAdapter(new ListBaseAdapter(datas, listview) {

			@Override
			public List<AppInfo> onload() {
				HomeProtocol hProtocol = new HomeProtocol();
				List<AppInfo> load = hProtocol.load(datas.size());
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

	public LoadResult load() {
		HomeProtocol hProtocol = new HomeProtocol();
		datas = hProtocol.load(0);
		pictures = hProtocol.getPictures();// 获取轮播图片数据
		return checkData(datas);
	}

}
