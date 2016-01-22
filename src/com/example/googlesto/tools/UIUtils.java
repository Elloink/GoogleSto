package com.example.googlesto.tools;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.googlesto.BaseApplication;

public class UIUtils {
	/**
	 * 获取到字符数组
	 * 
	 * @param tabNames
	 *            数组id
	 */
	public static String[] getStringArray(int tabNames) {
		return getResource().getStringArray(tabNames);
	}

	public static Resources getResource() {
		return BaseApplication.getApplication().getResources();
	}

	/**
	 * 获取上下文
	 * 
	 * @return
	 */
	public static Context getContext() {
		return BaseApplication.getApplication();
	}

	/** dip转换px */
	public static int dip2px(int dip) {
		final float scale = getResource().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

	/** pxz转换dip */

	public static int px2dip(int px) {
		final float scale = getResource().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}
	public static View inflate(int id) {
		return View.inflate(getContext(), id, null);
	}
	/**
	 * 把runnable方法提价到主线程运行
	 * 
	 * @param runnable
	 */
	public static void runOnUiThread(Runnable runnable) {
		// 判断当前线程是否在主线程运行
		if (android.os.Process.myTid() == BaseApplication.getMainTid()) {
			runnable.run();
		} else {
			// 获取handler
			BaseApplication.getHandler().post(runnable);
		}
	}
/**
 * 获取图片
 * @param id
 * @return
 */
	public static Drawable getDrawalbe(int id) {
		return getResource().getDrawable(id);
	}
}
