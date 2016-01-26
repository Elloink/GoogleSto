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
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class DetailInfoHolder extends BaseHoder<AppInfo> {
	@ViewInject(R.id.item_icon)
	private ImageView item_icon;
	@ViewInject(R.id.item_title)
	private TextView item_title;
	@ViewInject(R.id.item_rating)
	private RatingBar item_rating;
	@ViewInject(R.id.item_download)
	private TextView item_download;
	@ViewInject(R.id.item_version)
	private TextView item_version;
	@ViewInject(R.id.item_date)
	private TextView item_date;
	@ViewInject(R.id.item_size)
	private TextView item_size;

	@Override
	public View initView() {
		View view=UIUtils.inflate(R.layout.detail_app_info);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void refreshView(AppInfo data) {
		bitmapUtils.display(item_icon, GlobalContants.URL+"image?name="+data.getIconUrl());
		item_title.setText(data.getName());
		item_rating.setRating(data.getStars());
		item_download.setText("下载:"+data.getDownloadNum());
		item_version.setText("版本:"+data.getVersion());
		item_date.setText("时间:"+data.getDate());
		item_size.setText("大小:"+Formatter.formatFileSize(UIUtils.getContext(), data.getSize()));
	}

}
