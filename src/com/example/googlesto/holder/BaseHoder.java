package com.example.googlesto.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.googlesto.R;
import com.example.googlesto.domin.AppInfo;
import com.example.googlesto.globle.GlobalContants;
import com.example.googlesto.tools.UIUtils;
import com.lidroid.xutils.BitmapUtils;

public abstract class BaseHoder<Data> {

	private View contentview;
	Data datas;
	BitmapUtils bitmapUtils;
	public void setDatas(Data datas) {
		this.datas = datas;
		refreshView(datas);
	}

	public View getContentview() {
		return contentview;
	}

	public BaseHoder() {
		bitmapUtils = new BitmapUtils(UIUtils.getContext());
		contentview = initView();
		contentview.setTag(this);
	}

	public abstract View  initView();

	public abstract void refreshView(Data datas) ;
	
	
}
