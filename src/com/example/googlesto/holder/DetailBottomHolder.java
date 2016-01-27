package com.example.googlesto.holder;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.googlesto.R;
import com.example.googlesto.domin.AppInfo;
import com.example.googlesto.tools.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class DetailBottomHolder extends BaseHoder<AppInfo> implements OnClickListener {
	@ViewInject(R.id.bottom_favorites)
	Button bottom_favorites;
	@ViewInject(R.id.bottom_share)
	Button bottom_share;
	@ViewInject(R.id.progress_btn)
	Button progress_btn;
	@Override
	public View initView() {
		View view =  UIUtils.inflate(R.layout.detail_bottomholder);
		ViewUtils.inject(this,view);
		return view;
	}

	@Override
	public void refreshView(AppInfo datas) {
		bottom_favorites.setOnClickListener(this);
		bottom_share.setOnClickListener(this);
		progress_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bottom_favorites:
			Toast.makeText(UIUtils.getContext(), " ’≤ÿ", 0).show();
			break;
		case R.id.bottom_share:
			Toast.makeText(UIUtils.getContext(), "∑÷œÌ", 0).show();
			break;
		case R.id.progress_btn:
			Toast.makeText(UIUtils.getContext(), "œ¬‘ÿ", 0).show();
			break;
		}		
	}

}
