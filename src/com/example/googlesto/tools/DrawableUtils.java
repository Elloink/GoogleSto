package com.example.googlesto.tools;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

public class DrawableUtils {
	/**
	 * ����Բ�� ��ɫ
	 * @param color
	 * @return
	 */
	public static GradientDrawable createShape(int color){
		
		GradientDrawable drawable=new GradientDrawable();
		drawable.setCornerRadius(UIUtils.dip2px(5));//����4���ǵĻ��� 
		drawable.setColor(color);// ������ɫ
		return drawable;
		
		
	}
	/**
	 * ����״̬ѡ����
	 * @param pressedDrawable
	 * @param normalDrawable
	 * @return
	 */
	public static StateListDrawable createSelectorDrawable(Drawable pressedDrawable,Drawable normalDrawable){
//		<selector xmlns:android="http://schemas.android.com/apk/res/android"  android:enterFadeDuration="200">
//	    <item  android:state_pressed="true" android:drawable="@drawable/detail_btn_pressed"></item>
//	    <item  android:drawable="@drawable/detail_btn_normal"></item>
//	</selector>
		//״̬ѡ����
		StateListDrawable stateListDrawable=new StateListDrawable();
		stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);// ������ʾ��ͼƬ
		stateListDrawable.addState(new int[]{}, normalDrawable);// ̧����ʾ��ͼƬ
		return stateListDrawable;
		
	}
}
