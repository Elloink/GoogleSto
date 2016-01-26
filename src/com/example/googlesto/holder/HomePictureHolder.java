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

	/* 当new HomePictureHolder()就会调用该方法 */
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
		viewPager.setCurrentItem(2000 * datas.size());// 设置起始的位置
														// Integer.Max_Vlue/2
		viewPager.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					runTask.stop();   
					break;
				case MotionEvent.ACTION_CANCEL:  // 事件的取消
				case MotionEvent.ACTION_UP:
					runTask.start();
					break;
				}
				return false;//返回值一定要false，如果为true阻碍viewpager正常滑动
			}
		});
		runTask = new AutoRunTask();//开始滑动
		runTask.start();
	}
	boolean flag;
	private AutoRunTask runTask;
	//自动轮播
	class AutoRunTask implements Runnable{
		@Override
		public void run() {
			if(flag){
				UIUtils.cancel(this);  // 取消之前
				int currentItem = viewPager.getCurrentItem();
				currentItem++;
				viewPager.setCurrentItem(currentItem);
				//  延迟执行当前的任务
				UIUtils.postDelayed(this, 2000);// 递归调用
		}
		}
			public void start(){
				if(!flag){
					UIUtils.cancel(this);  // 取消之前
					flag=true;
					UIUtils.postDelayed(this, 2000);// 递归调用
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
		// 当前viewPager里面有多少个条目
		LinkedList<ImageView> convertView = new LinkedList<ImageView>();

		// 当前viewPager里面有多少个条目
		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		/* 判断返回的对象和 加载view对象的关系 */
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ImageView view = (ImageView) object;
			convertView.add(view);// 把移除的对象 添加到缓存集合中
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
			container.addView(view);// 加载的对像
			return view;// 返回的对象
		}
	}

}
