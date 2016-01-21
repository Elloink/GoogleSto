package com.example.googlesto.fragment;

import java.util.Random;

import com.example.googlesto.R;
import com.example.googlesto.protocol.HomeProtocol;
import com.example.googlesto.view.LoadingPage.LoadResult;


//import android.R;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

public class HomeFragment extends BaseFragment {
	//当此fragment挂载的activity创建的时候调用show（）；因为刚加载的时候，fragment没有滑动，所以不能调用show()
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		show();
	}
	// 创建成功界面
	public View createSuccessView() {
		TextView tv = new TextView(getActivity());
		tv.setText("加载成功了....");
		tv.setTextSize(30);
		return tv;
	}
	
	public LoadResult load() {
		HomeProtocol hProtocol = new HomeProtocol();
		hProtocol.load(0);
		return LoadResult.success;
	}


}
