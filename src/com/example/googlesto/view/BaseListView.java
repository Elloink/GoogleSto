package com.example.googlesto.view;

import com.example.googlesto.R;
import com.example.googlesto.tools.UIUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class BaseListView extends ListView {

	public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public BaseListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BaseListView(Context context) {
		super(context);
		init();
	}

	private void init() {
//		setSelector  点击显示的颜色
//		setCacheColorHint  拖拽的颜色
//		setDivider  每个条目的间隔	的分割线	
		this.setSelector(R.drawable.nothing);
		this.setCacheColorHint(R.drawable.nothing);
		this.setDivider(UIUtils.getDrawalbe(R.drawable.nothing));
		
	}

}
