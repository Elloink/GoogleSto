package com.example.googlesto.fragment;

import java.util.List;
import java.util.Random;

import com.example.googlesto.R;
import com.example.googlesto.domin.AppInfo;
import com.example.googlesto.globle.GlobalContants;
import com.example.googlesto.protocol.HomeProtocol;
import com.example.googlesto.tools.BitmapHelper;
import com.example.googlesto.tools.UIUtils;
import com.example.googlesto.view.BaseListView;
import com.example.googlesto.view.LoadingPage.LoadResult;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;

import android.graphics.drawable.Drawable;
//import android.R;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ViewUtils;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class HomeFragment extends BaseFragment {
	private List<AppInfo> datas;
	// 当此fragment挂载的activity创建的时候调用show（）；因为刚加载的时候，fragment没有滑动，所以不能调用show()
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		show();
	}

	// 创建成功界面
	public View createSuccessView() {
		BaseListView listview = new BaseListView(getContext());
		bitmapUtils = BitmapHelper.getBitmapUtils();
		listview.setAdapter(new HomeAdapter());
		listview.setOnScrollListener(new PauseOnScrollListener(bitmapUtils,
				false, true));
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default); // 设置如果图片加载中显示的图片
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);// 加载失败显示的图片
		return listview;
	}

	class HomeAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public Object getItem(int arg0) {
			return datas.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			View view;
			ViewHolder holder;
			if (arg1 == null) {
				view = View.inflate(getContext(), R.layout.item_app, null);

				holder = new ViewHolder();
				holder.item_icon = (ImageView) view
						.findViewById(R.id.item_icon);
				holder.item_title = (TextView) view
						.findViewById(R.id.item_title);
				holder.item_size = (TextView) view.findViewById(R.id.item_size);
				holder.item_bottom = (TextView) view
						.findViewById(R.id.item_bottom);
				holder.item_rating = (RatingBar) view
						.findViewById(R.id.item_rating);
				view.setTag(holder);
			} else {
				view = arg1;
				holder = (ViewHolder) view.getTag();
			}
			AppInfo appInfo = datas.get(arg0);
			holder.item_title.setText(appInfo.getName());// 设置应用程序的名字
			String size = Formatter.formatFileSize(UIUtils.getContext(),
					appInfo.getSize());// 应用程序大小
			holder.item_size.setText(size);
			holder.item_bottom.setText(appInfo.getDes());// 描述信息
			float stars = appInfo.getStars();
			holder.item_rating.setRating(stars); // 评分设置ratingBar的值
			String iconUrl = appInfo.getIconUrl(); // http://127.0.0.1:8090/image?name=app/com.youyuan.yyhl/icon.jpg

			// 显示图片的控件
			bitmapUtils.display(holder.item_icon, GlobalContants.URL
					+ "image?name=" + iconUrl);
			return view;
		}

	}

	static class ViewHolder {
		ImageView item_icon;
		TextView item_title, item_size, item_bottom;
		RatingBar item_rating;
	}

	public LoadResult load() {
		HomeProtocol hProtocol = new HomeProtocol();
		 datas = hProtocol.load(0);
		return checkData(datas);
	}

	

}
