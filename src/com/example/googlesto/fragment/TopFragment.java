package com.example.googlesto.fragment;

import java.util.List;
import java.util.Random;

import com.example.googlesto.R;
import com.example.googlesto.protocol.TopProtocol;
import com.example.googlesto.tools.DrawableUtils;
import com.example.googlesto.tools.UIUtils;
import com.example.googlesto.view.LoadingPage.LoadResult;

import android.bluetooth.le.ScanCallback;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class TopFragment extends BaseFragment {
	private List<String> data;

	@Override
	public View createSuccessView() {
		ScrollView scrollView = new ScrollView(UIUtils.getContext());
		scrollView.setBackgroundResource(R.drawable.grid_item_bg_normal);
		LinearLayout layout = new LinearLayout(UIUtils.getContext());
		int backColor = 0xffcecece;
		Drawable pressedDrawable=DrawableUtils.createShape(backColor);// 按下显示的图片
		layout.setOrientation(LinearLayout.VERTICAL);
		for (int i = 0; i < data.size(); i++) {
			TextView textView = new TextView(UIUtils.getContext());
			final String str=data.get(i);
			textView.setText(str);
			Random random = new Random();  //创建随机
			int red = random.nextInt(200)+22;    
			int green = random.nextInt(200)+22;  
			int blue = random.nextInt(200)+22;     
			int color=Color.rgb(red, green, blue);//范围 0-255 
			
			GradientDrawable createshap = DrawableUtils.createShape(color);
			StateListDrawable selectorDrawable = DrawableUtils.createSelectorDrawable(pressedDrawable, createshap);//状态选择器
			textView.setBackgroundDrawable(selectorDrawable);
			textView.setTextColor(Color.WHITE);
			//textView.setTextSize(UiUtils.dip2px(14));
			int textPaddingV = UIUtils.dip2px(4);
			int textPaddingH = UIUtils.dip2px(7);
			textView.setPadding(textPaddingH, textPaddingV, textPaddingH, textPaddingV); //设置padding 
			textView.setClickable(true);//设置textView可以被点击
			textView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast.makeText(UIUtils.getContext(), str, 0).show();					
				}
			});
			
			layout.addView(textView,new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, -2));//-2包裹内容
		}
		scrollView.addView(layout);
		return scrollView;
	}

	@Override
	public LoadResult load() {
		TopProtocol protocol = new TopProtocol();
		data = protocol.load(0);
		return checkData(data);
	}

}
