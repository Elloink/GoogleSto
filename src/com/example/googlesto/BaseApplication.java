package com.example.googlesto;

import im.fir.sdk.FIR;
import android.app.Application;
import android.content.Context;
//��Ҫ���嵥�ļ���<application���ã� ���android:name="BaseApplication"˵����������Ӧ��
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
