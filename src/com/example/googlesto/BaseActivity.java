package com.example.googlesto;

import java.util.LinkedList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class BaseActivity extends ActionBarActivity {
	final static List<BaseActivity> mActivity = new LinkedList<BaseActivity>();
	//第二种杀死应用程序的方法1
//	private KillAllReceiver receiver;
//	private class KillAllReceiver extends BroadcastReceiver {
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			finish();
//		}
//		
//	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//第二种杀死应用程序的方法2
//		receiver = new KillAllReceiver();
//		IntentFilter filter = new IntentFilter("com.googles.threelu.killall");
//		registerReceiver(receiver, filter);
		
	//3最后只需要在其子类activity中添加  如下代码即可关闭当前的activity，sendBroadcast(new Intent("com.googles.threelu.killall"));
		synchronized (mActivity) {//同步代码块防止出现还没加载就删除，线程安全，以下相同
			// 当activity创建的时候，添加activity到集合中
			mActivity.add(this);
		}
		init();
		initView();
		initActionBar();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 当activity销毁的时候，从集合中移除
		synchronized (mActivity) {
			mActivity.remove(this);
		}
		//4当程序退出的时候需要反注册广播接收者
//		if(receiver!=null){
//		unregisterReceiver(receiver);
//		receiver=null;
//	}
	}

	/**
	 * 第一种杀死activity的方法
	 * 移除所有的activity
	 */
	public void killAll() {
		// 复制了一份mActivity
		List<BaseActivity> copy;
		synchronized (mActivity) {
			 copy = new LinkedList<BaseActivity>(mActivity);
		}
		for (BaseActivity activity : copy) {
			activity.finish();
		}
		// 杀死当前的进程
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 初始
	 */
	protected void init() {

	}

	/**
	 * 初始化actionbar
	 */
	protected void initActionBar() {

	}

	/**
	 * 初始化view
	 */
	protected void initView() {

	}

}
