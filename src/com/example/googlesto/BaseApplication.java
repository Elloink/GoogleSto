package com.example.googlesto;

import im.fir.sdk.FIR;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
//需要在清单文件中<application配置， 添加android:name="BaseApplication"说明此类代表此应用
public class BaseApplication extends Application {
	private static BaseApplication application;
	private static int mainTid;//当前主线程的id
	private static Handler handler;//主线程的handler
	
	@Override
	public void onCreate() {
		super.onCreate();
		 FIR.init(this);
		application = this;
		mainTid = android.os.Process.myTid();
		handler = new Handler();
	}
	public static Context getApplication(){
		return application;
	}
	public static int getMainTid() {
		return mainTid;
	}
	public static Handler getHandler() {
		return handler;
	}

}
