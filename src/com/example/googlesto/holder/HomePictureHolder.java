package com.example.googlesto.holder;

import java.util.List;

import com.example.googlesto.globle.GlobalContants;
import com.example.googlesto.tools.UIUtils;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.AbsListView.LayoutParams;

public class HomePictureHolder extends BaseHoder<List<String>> {
	
	private ViewPager viewPager;
	private List<String> datas;
	/*当new HomePictureHolder()就会调用该方法 */
	@Override
	public View initView() {
		FrameLayout frameLayout =new FrameLayout(UIUtils.getContext());
		frameLayout.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,240));
		viewPager = new ViewPager(UIUtils.getContext());
		viewPager.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,240));
		frameLayout.addView(viewPager);
		return frameLayout;
	}

	@Override
	public void refreshView(List<String> datas) {
		this.datas=datas;
		viewPager.setAdapter(new HomeAdapter());	
	}
	class HomeAdapter extends PagerAdapter{
		// 当前viewPager里面有多少个条目
		@Override
		public int getCount() {
			return datas.size();
		}
		/*判断返回的对象和 加载view对象的关系*/
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
//			super.destroyItem(container, position, object);
			container.removeView((View) object);
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView img = new ImageView(UIUtils.getContext());
			bitmapUtils.display(img, GlobalContants.URL+"image?name="+datas.get(position));
			container.addView(img);//加载的对像
			return img;//返回的对象
		}
	}

}
