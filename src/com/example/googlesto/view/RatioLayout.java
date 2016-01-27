package com.example.googlesto.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class RatioLayout extends FrameLayout {
	private float ratio = 2.34f;
	

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	// ���տ�߱�����ʾ
	public RatioLayout(Context context) {
		super(context);
	}

	public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// ����1 �����ؼ� ����2 ���Ե����� ����3 Ĭ�ϵ�ֵ
				float ratio = attrs.getAttributeFloatValue(
						"http://schemas.android.com/apk/res/com.example.googlesto",
						"ratio", 2.43f);
				setRatio(ratio);
	}

	public RatioLayout(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	// ������ǰ����
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// widthMeasureSpec ��ȵĹ��� ���������� ģʽ ֵ
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);// ģʽ
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);// ��ȴ�С�������ڱ߽�padding
		int width = widthSize - getPaddingLeft() - getPaddingRight();// ʵ�ʿ��

		int heightMode = MeasureSpec.getMode(heightMeasureSpec); // ģʽ
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);// �߶ȴ�С
		int height = heightSize - getPaddingTop() - getPaddingBottom();// ȥ���������ߵ�padding��ʵ�ʸ߶�
		if (widthMode == MeasureSpec.EXACTLY
				&& heightMode != MeasureSpec.EXACTLY) {// ����һ�¸߶ȵ�ֵ �ø߶�=���/����
			height = (int) (width / ratio + 0.5f);
		} else if (widthMode != MeasureSpec.EXACTLY
				&& heightMode == MeasureSpec.EXACTLY) {// �߶��Ǿ�ȷ��ֵ
			// ���=�߶�*ratio.�����߶ȱ仯���仯
			width = (int) ((height * ratio) + 0.5f);
		}
		//���������µĹ���
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,
				width + getPaddingLeft() + getPaddingRight());
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,
				height + getPaddingTop() + getPaddingBottom());
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
