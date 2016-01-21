package com.example.googlesto;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Detail_activity extends BaseActivity {

	@Override
	protected void initView() {
		setContentView(R.layout.activity_detail);
		super.initView();
	}
	@Override
	protected void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		super.initActionBar();
	}


}
