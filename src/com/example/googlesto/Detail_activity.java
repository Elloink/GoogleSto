package com.example.googlesto;

import com.example.googlesto.domin.AppInfo;
import com.example.googlesto.holder.DetailInfoHolder;
import com.example.googlesto.holder.DetailSafeHolder;
import com.example.googlesto.holder.DetailSreenHolder;
import com.example.googlesto.protocol.DetailProtocol;
import com.example.googlesto.tools.UIUtils;
import com.example.googlesto.view.LoadingPage;
import com.example.googlesto.view.LoadingPage.LoadResult;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

public class Detail_activity extends BaseActivity {
	private String packageName;
	private AppInfo data;

	@Override
	protected void initView() {
		LoadingPage loadingpager = new LoadingPage(getApplicationContext()) {

			@Override
			public LoadResult load() {
				return Detail_activity.this.load();
			}

			@Override
			public View createSuccessView() {
				return Detail_activity.this.createSuccessView();
			}
		};
		loadingpager.show();
		setContentView(loadingpager);
	}

	private FrameLayout bottom_layout, detail_info, detail_safe, detail_des;
	private HorizontalScrollView detail_screen;
	private DetailInfoHolder detailInfoHolder;

	private DetailSreenHolder screenHolder;
	private DetailSafeHolder safeHolder;

	// private DetailDesHolder desHolder;

	protected View createSuccessView() {
		View view = UIUtils.inflate(R.layout.activity_detail);
		// 添加信息区域
		bottom_layout = (FrameLayout) view.findViewById(R.id.bottom_layout);

		// 操作 应用程序信息
		detail_info = (FrameLayout) view.findViewById(R.id.detail_info);
		detailInfoHolder = new DetailInfoHolder();
		detailInfoHolder.setDatas(data);
		detail_info.addView(detailInfoHolder.getContentview());

		// 安全标记
		detail_safe = (FrameLayout) view.findViewById(R.id.detail_safe);
		safeHolder = new DetailSafeHolder();
		safeHolder.setDatas(data);
		detail_safe.addView(safeHolder.getContentview());

		detail_des = (FrameLayout) view.findViewById(R.id.detail_des);

		// 中间5张图片
		detail_screen = (HorizontalScrollView) view
				.findViewById(R.id.detail_screen);
		screenHolder = new DetailSreenHolder();
		screenHolder.setDatas(data);
		detail_screen.addView(screenHolder.getContentview());

		return view;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Intent intent = getIntent(); // 获取到打开当前activity的意图对象
		packageName = intent.getStringExtra("packageName");
		System.out.println(packageName);
		super.onCreate(savedInstanceState);
	}

	protected LoadResult load() {
		DetailProtocol detailProtocol = new DetailProtocol(packageName);
		data = detailProtocol.load(0);
		if (data == null) {
			return LoadResult.error;
		} else {
			return LoadResult.success;
		}
	}

	@Override
	protected void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		super.initActionBar();
	}

}
