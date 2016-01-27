package com.example.googlesto.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class RatioLayout extends FrameLayout {
	private float ratio = 2.34f;
	

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	// 按照宽高比例显示
	public RatioLayout(Context context) {
		super(context);
	}

	public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public RatioLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// 测量当前布局
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// widthMeasureSpec 宽度的规则 包含两部分 模式 值
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);// 模式
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);// 宽度大小，包含内边界padding
		int width = widthSize - getPaddingLeft() - getPaddingRight();// 实际宽度

		int heightMode = MeasureSpec.getMode(heightMeasureSpec); // 模式
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);// 高度大小
		int height = heightSize - getPaddingTop() - getPaddingBottom();// 去掉上下两边的padding，实际高度
		if (widthMode == MeasureSpec.EXACTLY
				&& heightMode != MeasureSpec.EXACTLY) {// 修正一下高度的值 让高度=宽度/比例
			height = (int) (width / ratio + 0.5f);
		} else if (widthMode != MeasureSpec.EXACTLY
				&& heightMode == MeasureSpec.EXACTLY) {// 高度是精确的值
			// 宽度=高度*ratio.宽度随高度变化而变化
			width = (int) ((height * ratio) + 0.5f);
		}
		//重新制作新的规则
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,
				width + getPaddingLeft() + getPaddingRight());
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,
				height + getPaddingTop() + getPaddingBottom());
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
