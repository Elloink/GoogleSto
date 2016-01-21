package com.example.googlesto;

import im.fir.sdk.FIR;
import android.app.Application;
import android.content.Context;
//需要在清单文件中<application配置， 添加android:name="BaseApplication"说明此类代表此应用
public class BaseApplication extends Application {
	private static BaseApplication application;
	@Override
	public void onCreate() {
		super.onCreate();
		 FIR.init(this);
		application = this;
	}
	public static Context getApplication(){
		return application;
	}


}
