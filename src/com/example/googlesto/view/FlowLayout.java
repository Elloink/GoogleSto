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
	private int horizontolSpacing = UIUtils.dip2px(13);// ��ֱ���
	private int verticalSpacing = UIUtils.dip2px(13);// ˮƽ���

	public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FlowLayout(Context context) {
		super(context);
	}

	private Line curentLine;// ��ǰ��
	private int useWidth = 0;// ��ǰ��ʹ�ÿ��
	private List<Line> mlines = new ArrayList<Line>();
	private int width;// ��ǰ�ؼ��ܵĿ��

	// ������ǰ�ؼ� flowlayout
	// �������������ÿ�����ӵ�
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		mlines.clear();// ���֮ǰ����
		curentLine = null;
		useWidth = 0;

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec); // ��ȡ��ǰ������(Flowlayout)��ģʽ
		width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft()
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
		// ���ӿ�ߵĹ���
		int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
				childeWidthMode, width);
		int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
				childeHeightMode, height);
		curentLine = new Line();// �����˵�һ��
		for (int i = 0; i < getChildCount(); i++) {// ����ÿ������
			View child = getChildAt(i);// ��ȡ����
			child.measure(childWidthMeasureSpec, childHeightMeasureSpec);// ��������Ĺ������

			int measuredWidth = child.getMeasuredWidth();// ��ȡ��ǰ���ӵĿ��
			useWidth += measuredWidth;// �õ�ǰ�м���ʹ�õĳ���
			if (useWidth <= width) {
				curentLine.addChild(child);// ��ʱ��֤����ǰ�ĺ����ǿ��ԷŽ���ǰ������,�Ž�ȥ
				useWidth += horizontolSpacing;
				if (useWidth > width) {
					// ����
					newLine();
				}
			} else {
				// ����
				if (curentLine.getChildCount() < 1) {
					curentLine.addChild(child); // ��֤��ǰ������������һ������
				}
				newLine();
			}

		}
		if (!mlines.contains(curentLine)) {
			mlines.add(curentLine);// ������һ��
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
		mlines.add(curentLine);// ��¼֮ǰ����
		curentLine = new Line(); // �����µ�һ��
		useWidth = 0;
	}

	/**
	 * ���ڼ�¼���ڵ���
	 * 
	 * @author threes
	 *
	 */
	private class Line {
		int height = 0; // ��ǰ�еĸ߶�
		int lineWidth = 0;
		private List<View> children = new ArrayList<View>();

		/**
		 * ���һ������
		 * 
		 * @param child
		 */
		public void addChild(View child) {
			children.add(child);
			if (child.getMeasuredHeight() > height) {// �߶������Ϊ��
				height = child.getMeasuredHeight();
			}
			lineWidth += child.getMeasuredWidth();
		}

		public void layout(int l, int t) {
			lineWidth += horizontolSpacing * (children.size() - 1);
			int surplusChild = 0;// ÿ�����Ӷ���Ŀռ�
			int surplus = width - lineWidth;// ʣ���С
			if (surplus > 0) {
				surplusChild = surplus / children.size();
			}
			for (int i = 0; i < children.size(); i++) {
				View child = children.get(i);
				// getMeasuredWidth() �ؼ�ʵ�ʵĴ�С
				// getWidth() �ؼ���ʾ�Ĵ�С
				child.layout(l, t, l + child.getMeasuredWidth() + surplusChild,
						t + child.getMeasuredHeight());
				// �ٲ���ȫ�ָ���Ч��
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

	// ����ÿ�����ӵ�λ��
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		l += getPaddingLeft();
		t += getPaddingTop();
		for (int i = 0; i < mlines.size(); i++) {
			Line line = mlines.get(i);
			line.layout(l, t); // ����ÿһ��ȥ����
			t += line.getHeight() + verticalSpacing;
		}
	}
}
