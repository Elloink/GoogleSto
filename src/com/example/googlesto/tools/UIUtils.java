package com.example.googlesto.tools;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.googlesto.BaseApplication;

public class UIUtils {
	/**
	 * ��ȡ���ַ�����
	 * 
	 * @param tabNames
	 *            ����id
	 */
	public static String[] getStringArray(int tabNames) {
		return getResource().getStringArray(tabNames);
	}

	public static Resources getResource() {
		return BaseApplication.getApplication().getResources();
	}

	/**
	 * ��ȡ������
	 * 
	 * @return
	 */
	public static Context getContext() {
		return BaseApplication.getApplication();
	}

	/** dipת��px */
	public static int dip2px(int dip) {
		final float scale = getResource().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

	/** pxzת��dip */

	public static int px2dip(int px) {
		final float scale = getResource().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}
	public static View inflate(int id) {
		return View.inflate(getContext(), id, null);
	}
	/**
	 * ��runnable������۵����߳�����
	 * 
	 * @param runnable
	 */
	public static void runOnUiThread(Runnable runnable) {
		// �жϵ�ǰ�߳��Ƿ������߳�����
		if (android.os.Process.myTid() == BaseApplication.getMainTid()) {
			runnable.run();
		} else {
			// ��ȡhandler
			BaseApplication.getHandler().post(runnable);
		}
	}
/**
 * ��ȡͼƬ
 * @param id
 * @return
 */
	public static Drawable getDrawalbe(int id) {
		return getResource().getDrawable(id);
	}
}
