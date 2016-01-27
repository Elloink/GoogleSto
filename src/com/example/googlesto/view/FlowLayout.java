package com.example.googlesto.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {
	// ����ÿ�����ӵ�λ��
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

	}

	public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FlowLayout(Context context) {
		super(context);
	}

	// ������ǰ�ؼ� flowlayout
	// �������������ÿ�����ӵ�
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec); // ��ȡ��ǰ������(Flowlayout)��ģʽ
		int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft()
				- getPaddingRight();
		int height = MeasureSpec.getSize(heightMeasureSpec)
				- getPaddingBottom() - getPaddingTop(); // ��ȡ����͸�
		int childeWidthMode;
		int childeHeightMode;
		// Ϊ�˲���ÿ������ ��Ҫָ��ÿ�����Ӳ�������
		childeWidthMode = widthMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST
				: widthMode;
		childeHeightMode = heightMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST
				: heightMode;
		//  ���ӿ�ߵĹ���
		int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
				childeWidthMode, width);
		int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
				childeHeightMode, height);
		for (int i = 0; i < getChildCount(); i++) {//����ÿ������
			View child = getChildAt(i);//��ȡ����
			child.measure(childWidthMeasureSpec, childHeightMeasureSpec);//��������Ĺ������
		}
		setMeasuredDimension(
				getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
				getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
	}

}
