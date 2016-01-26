package com.example.googlesto;

import com.example.googlesto.domin.AppInfo;
import com.example.googlesto.protocol.DetailProtocol;
import com.example.googlesto.view.LoadingPage;
import com.example.googlesto.view.LoadingPage.LoadResult;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Detail_activity extends BaseActivity {
	String packageName;
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
		super.initView();
	}
	protected View createSuccessView() {
		return null;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Intent intent = getIntent();  // 获取到打开当前activity的意图对象
		packageName = intent.getStringExtra("packageName");
		super.onCreate(savedInstanceState);
	}
	protected LoadResult load() {
		DetailProtocol detailProtocol = new DetailProtocol(packageName);
		AppInfo appInfo = detailProtocol.load(0);
		if(appInfo==null){
			return LoadResult.error;
		}else{
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
