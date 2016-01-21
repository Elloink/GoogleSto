package com.example.googlesto;

import im.fir.sdk.FIR;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
//��Ҫ���嵥�ļ���<application���ã� ���android:name="BaseApplication"˵����������Ӧ��
public class BaseApplication extends Application {
	private static BaseApplication application;
	private static int mainTid;//��ǰ���̵߳�id
	private static Handler handler;//���̵߳�handler
	
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
