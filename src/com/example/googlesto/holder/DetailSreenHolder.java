package com.example.googlesto.holder;

import java.util.List;

import android.view.View;
import android.widget.ImageView;

import com.example.googlesto.R;
import com.example.googlesto.domin.AppInfo;
import com.example.googlesto.globle.GlobalContants;
import com.example.googlesto.tools.UIUtils;

public class DetailSreenHolder extends BaseHoder<AppInfo> {
	private ImageView[] ivs;
	@Override
	public View initView() {
		View view=UIUtils.inflate(R.layout.detail_screen);
		ivs=new ImageView[5];
		ivs[0]=(ImageView) view.findViewById(R.id.screen_1);
		ivs[1]=(ImageView) view.findViewById(R.id.screen_2);
		ivs[2]=(ImageView) view.findViewById(R.id.screen_3);
		ivs[3]=(ImageView) view.findViewById(R.id.screen_4);
		ivs[4]=(ImageView) view.findViewById(R.id.screen_5);
		return view;
	}

	@Override
	public void refreshView(AppInfo datas) {
		List<String> screen = datas.getScreen();
		for (int i = 0; i < 5; i++) {
			if (i<screen.size()) {
				ivs[i].setVisibility(View.VISIBLE);
				bitmapUtils.display(ivs[i], GlobalContants.URL+"image?name="+screen.get(i));
			}else {
				ivs[i].setVisibility(View.VISIBLE);
			}
		}
	}

}
