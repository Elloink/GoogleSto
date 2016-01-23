package com.example.googlesto.holder;

import com.example.googlesto.R;
import com.example.googlesto.domin.AppInfo;
import com.example.googlesto.globle.GlobalContants;
import com.example.googlesto.tools.UIUtils;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ListBaseHolder extends BaseHoder<AppInfo> {

	ImageView item_icon;
	TextView item_title, item_size, item_bottom;
	RatingBar item_rating;

	@Override
	public View initView() {
		View contentview = View.inflate(UIUtils.getContext(),
				R.layout.item_app, null);
		this.item_icon = (ImageView) contentview
				.findViewById(R.id.item_icon);
		this.item_title = (TextView) contentview
				.findViewById(R.id.item_title);
		this.item_size = (TextView) contentview
				.findViewById(R.id.item_size);
		this.item_bottom = (TextView) contentview
				.findViewById(R.id.item_bottom);
		this.item_rating = (RatingBar) contentview
				.findViewById(R.id.item_rating);
		return contentview;
	}

	@Override
	public void refreshView(AppInfo datas) {
		this.item_title.setText(datas.getName());// 设置应用程序的名字
		String size = Formatter.formatFileSize(UIUtils.getContext(),
				datas.getSize());// 应用程序大小
		this.item_size.setText(size);
		this.item_bottom.setText(datas.getDes());// 描述信息
		float stars = datas.getStars();
		this.item_rating.setRating(stars); // 评分设置ratingBar的值
		String iconUrl = datas.getIconUrl(); // http://127.0.0.1:8090/image?name=app/com.youyuan.yyhl/icon.jpg
		// 显示图片的控件
		bitmapUtils.display(this.item_icon, GlobalContants.URL
				+ "image?name=" + iconUrl);
	}

}
