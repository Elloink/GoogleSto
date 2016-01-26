package com.example.googlesto.holder;

import java.util.LinkedList;
import java.util.List;

import com.example.googlesto.R;
import com.example.googlesto.globle.GlobalContants;
import com.example.googlesto.tools.UIUtils;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.AbsListView.LayoutParams;

public class HomePictureHolder extends BaseHoder<List<String>> {

	private ViewPager viewPager;
	private List<String> datas;

	/* ��new HomePictureHolder()�ͻ���ø÷��� */
	@Override
	public View initView() {
		// FrameLayout frameLayout =new FrameLayout(UIUtils.getContext());
		// frameLayout.setLayoutParams(new
		// AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,240));
		viewPager = new ViewPager(UIUtils.getContext());
		viewPager.setLayoutParams(new AbsListView.LayoutParams(
				LayoutParams.MATCH_PARENT, UIUtils
						.setDumens(R.dimen.home_picture_height)));
		// frameLayout.addView(viewPager);
		return viewPager;
	}

	@Override
	public void refreshView(List<String> datas) {
		this.datas = datas;

		viewPager.setAdapter(new HomeAdapter());
		viewPager.setCurrentItem(2000 * datas.size());// ������ʼ��λ��
														// Integer.Max_Vlue/2
		viewPager.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					runTask.stop();   
					break;
				case MotionEvent.ACTION_CANCEL:  // �¼���ȡ��
				case MotionEvent.ACTION_UP:
					runTask.start();
					break;
				}
				return false;//����ֵһ��Ҫfalse�����Ϊtrue�谭viewpager��������
			}
		});
		runTask = new AutoRunTask();//��ʼ����
		runTask.start();
	}
	boolean flag;
	private AutoRunTask runTask;
	//�Զ��ֲ�
	class AutoRunTask implements Runnable{
		@Override
		public void run() {
			if(flag){
				UIUtils.cancel(this);  // ȡ��֮ǰ
				int currentItem = viewPager.getCurrentItem();
				currentItem++;
				viewPager.setCurrentItem(currentItem);
				//  �ӳ�ִ�е�ǰ������
				UIUtils.postDelayed(this, 2000);// �ݹ����
		}
		}
			public void start(){
				if(!flag){
					UIUtils.cancel(this);  // ȡ��֮ǰ
					flag=true;
					UIUtils.postDelayed(this, 2000);// �ݹ����
				}
			}
			public  void stop(){
				if(flag){
					flag=false;
					UIUtils.cancel(this);
				}
			}
	} 

	class HomeAdapter extends PagerAdapter {
		// ��ǰviewPager�����ж��ٸ���Ŀ
		LinkedList<ImageView> convertView = new LinkedList<ImageView>();

		// ��ǰviewPager�����ж��ٸ���Ŀ
		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		/* �жϷ��صĶ���� ����view����Ĺ�ϵ */
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ImageView view = (ImageView) object;
			convertView.add(view);// ���Ƴ��Ķ��� ��ӵ����漯����
			container.removeView(view);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			int index = position % datas.size();
			ImageView view;
			if (convertView.size() > 0) {
				view = convertView.remove(0);
			} else {
				view = new ImageView(UIUtils.getContext());
			}
			bitmapUtils.display(view,
					GlobalContants.URL + "image?name=" + datas.get(index));
			container.addView(view);// ���صĶ���
			return view;// ���صĶ���
		}
	}

}
