package com.example.googlesto.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import com.example.googlesto.R;
import com.example.googlesto.domin.CategoryInfo;
import com.example.googlesto.fragment.CatagoryFragment;
import com.example.googlesto.tools.UIUtils;

public class CategoryTitleHolder extends BaseHoder<CategoryInfo> {
	private TextView tv;
	@Override
	public View initView() {
		tv = new TextView(UIUtils.getContext());
		tv.setTextColor(Color.BLACK);
		tv.setBackgroundDrawable(UIUtils.getDrawalbe(R.drawable.grid_item_bg));
		return tv;
	}

	@Override
	public void refreshView(CategoryInfo datas) {
		tv.setText(datas.getTitle());
	}

}
