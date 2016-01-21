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
	//�ڶ���ɱ��Ӧ�ó���ķ���1
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
		//�ڶ���ɱ��Ӧ�ó���ķ���2
//		receiver = new KillAllReceiver();
//		IntentFilter filter = new IntentFilter("com.googles.threelu.killall");
//		registerReceiver(receiver, filter);
		
	//3���ֻ��Ҫ��������activity�����  ���´��뼴�ɹرյ�ǰ��activity��sendBroadcast(new Intent("com.googles.threelu.killall"));
		synchronized (mActivity) {//ͬ��������ֹ���ֻ�û���ؾ�ɾ�����̰߳�ȫ��������ͬ
			// ��activity������ʱ�����activity��������
			mActivity.add(this);
		}
		init();
		initView();
		initActionBar();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ��activity���ٵ�ʱ�򣬴Ӽ������Ƴ�
		synchronized (mActivity) {
			mActivity.remove(this);
		}
		//4�������˳���ʱ����Ҫ��ע��㲥������
//		if(receiver!=null){
//		unregisterReceiver(receiver);
//		receiver=null;
//	}
	}

	/**
	 * ��һ��ɱ��activity�ķ���
	 * �Ƴ����е�activity
	 */
	public void killAll() {
		// ������һ��mActivity
		List<BaseActivity> copy;
		synchronized (mActivity) {
			 copy = new LinkedList<BaseActivity>(mActivity);
		}
		for (BaseActivity activity : copy) {
			activity.finish();
		}
		// ɱ����ǰ�Ľ���
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * ��ʼ
	 */
	protected void init() {

	}

	/**
	 * ��ʼ��actionbar
	 */
	protected void initActionBar() {

	}

	/**
	 * ��ʼ��view
	 */
	protected void initView() {

	}

}
