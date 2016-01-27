package com.example.googlesto.view;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import com.example.googlesto.tools.UIUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {
	private int horizontolSpacing = UIUtils.dip2px(13);// 垂直间隔
	private int verticalSpacing = UIUtils.dip2px(13);// 水平间隔

	public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FlowLayout(Context context) {
		super(context);
	}

	private Line curentLine;// 当前行
	private int useWidth = 0;// 当前行使用宽度
	private List<Line> mlines = new ArrayList<Line>();
	private int width;// 当前控件总的宽度

	// 测量当前控件 flowlayout
	// 父类有义务测量每个孩子的
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		mlines.clear();// 清空之前的行
		curentLine = null;
		useWidth = 0;

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec); // 获取当前父容器(Flowlayout)的模式
		width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft()
				- getPaddingRight();
		int height = MeasureSpec.getSize(heightMeasureSpec)
				- getPaddingBottom() - getPaddingTop(); // 获取到宽和高
		int childeWidthMode;
		int childeHeightMode;
		// 为了测量每个孩子 需要指定每个孩子测量规则
		childeWidthMode = widthMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST
				: widthMode;
		childeHeightMode = heightMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST
				: heightMode;
		// 孩子宽高的规则
		int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
				childeWidthMode, width);
		int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
				childeHeightMode, height);
		curentLine = new Line();// 创建了第一行
		for (int i = 0; i < getChildCount(); i++) {// 测量每个孩子
			View child = getChildAt(i);// 获取孩子
			child.measure(childWidthMeasureSpec, childHeightMeasureSpec);// 根据上面的规则测量

			int measuredWidth = child.getMeasuredWidth();// 获取当前孩子的宽度
			useWidth += measuredWidth;// 让当前行加上使用的长度
			if (useWidth <= width) {
				curentLine.addChild(child);// 这时候证明当前的孩子是可以放进当前的行里,放进去
				useWidth += horizontolSpacing;
				if (useWidth > width) {
					// 换行
					newLine();
				}
			} else {
				// 换行
				if (curentLine.getChildCount() < 1) {
					curentLine.addChild(child); // 保证当前行里面最少有一个孩子
				}
				newLine();
			}

		}
		if (!mlines.contains(curentLine)) {
			mlines.add(curentLine);// 添加最后一行
		}
		int totalheight = 0;
		for (Line line : mlines) {
			totalheight += line.getHeight();
		}
		totalheight += verticalSpacing * (mlines.size() - 1) + getPaddingTop()
				+ getPaddingBottom();

		System.out.println(totalheight);
		setMeasuredDimension(width + getPaddingLeft() + getPaddingRight(),
				resolveSize(totalheight, heightMeasureSpec));
	}

	private void newLine() {
		mlines.add(curentLine);// 记录之前的行
		curentLine = new Line(); // 创建新的一行
		useWidth = 0;
	}

	/**
	 * 用于记录所在的行
	 * 
	 * @author threes
	 *
	 */
	private class Line {
		int height = 0; // 当前行的高度
		int lineWidth = 0;
		private List<View> children = new ArrayList<View>();

		/**
		 * 添加一个孩子
		 * 
		 * @param child
		 */
		public void addChild(View child) {
			children.add(child);
			if (child.getMeasuredHeight() > height) {// 高度以最高为主
				height = child.getMeasuredHeight();
			}
			lineWidth += child.getMeasuredWidth();
		}

		public void layout(int l, int t) {
			lineWidth += horizontolSpacing * (children.size() - 1);
			int surplusChild = 0;// 每个孩子额外的空间
			int surplus = width - lineWidth;// 剩余大小
			if (surplus > 0) {
				surplusChild = surplus / children.size();
			}
			for (int i = 0; i < children.size(); i++) {
				View child = children.get(i);
				// getMeasuredWidth() 控件实际的大小
				// getWidth() 控件显示的大小
				child.layout(l, t, l + child.getMeasuredWidth() + surplusChild,
						t + child.getMeasuredHeight());
				// 瀑布流全局覆盖效果
				// child.layout(l, t, l + getMeasuredWidth() + surplusChild,
				// t + getMeasuredHeight());
				l += child.getMeasuredWidth() + surplusChild;
				l += horizontolSpacing;
			}
		}

		public int getHeight() {
			return height;
		}

		public int getChildCount() {
			return children.size();
		}

	}

	// 分配每个孩子的位置
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		l += getPaddingLeft();
		t += getPaddingTop();
		for (int i = 0; i < mlines.size(); i++) {
			Line line = mlines.get(i);
			line.layout(l, t); // 交给每一行去分配
			t += line.getHeight() + verticalSpacing;
		}
	}
}
