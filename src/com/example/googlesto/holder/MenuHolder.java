package com.example.googlesto.holder;

import com.example.googlesto.R;
import com.example.googlesto.domin.UserInfo;
import com.example.googlesto.globle.GlobalContants;
import com.example.googlesto.manager.ThreadManager;
import com.example.googlesto.protocol.UserProtocol;
import com.example.googlesto.tools.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuHolder extends BaseHoder<UserInfo> implements OnClickListener {
	@ViewInject(R.id.photo_layout)
	private RelativeLayout photo_layout;
	@ViewInject(R.id.image_photo)
	private ImageView image_photo;
	@ViewInject(R.id.user_name)
	private TextView user_name;
	@ViewInject(R.id.user_email)
	private TextView user_email;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.menu_holder);
		ViewUtils.inject(this, view);
		photo_layout.setOnClickListener(this);
		return view;
	}

	@Override
	public void refreshView(UserInfo datas) {

		 user_name.setText(datas.getName());
		 user_email.setText(datas.getEmail());
		 String url = datas.getUrl();//image/user.png
		 bitmapUtils.display(image_photo,
		 GlobalContants.URL+"image?name="+url);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.photo_layout:
			// 连接服务器登录,线程池
			ThreadManager.getInstance().createLongPool().excute(new Runnable() {
				@Override
				public void run() {
					UserProtocol protocol = new UserProtocol();
					final UserInfo load = protocol.load(0);
					System.out.println(load.getUrl());
					UIUtils.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							setDatas(load);
						}
					});

				}
			});
		}
	}
}
