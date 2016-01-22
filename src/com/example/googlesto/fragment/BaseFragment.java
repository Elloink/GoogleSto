package com.example.googlesto.fragment;

import java.util.List;
import java.util.Random;

import com.example.googlesto.R;
import com.example.googlesto.view.LoadingPage;
import com.example.googlesto.view.LoadingPage.LoadResult;
import com.lidroid.xutils.BitmapUtils;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public abstract class BaseFragment extends Fragment {

	LoadingPage loadingpage;
	BitmapUtils bitmapUtils;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 bitmapUtils =new BitmapUtils(getContext());
		if (loadingpage == null) {// framelayout等于空的时候创建
			loadingpage = new LoadingPage(getActivity()) {
				@Override
				public LoadResult load() {
					// TODO Auto-generated method stub
					return BaseFragment.this.load();
				}

				@Override
				public View createSuccessView() {
					// TODO Auto-generated method stub
					return BaseFragment.this.createSuccessView();
				}
			};
		} else {
			com.example.googlesto.tools.ViewUtils.removeParent(loadingpage);// 移除framelayout之前的爹
		}

		// show();// 根据服务器的数据，切换状态
		return loadingpage;
	}

	/**
	 * 创建成功界面
	 * 
	 * @return
	 */
	public abstract View createSuccessView();

	/**
	 * 加载数据
	 * 
	 * @return 加载的结果
	 */
	public abstract LoadResult load();

	public void show() {
		if (loadingpage != null) {
			loadingpage.show();
		}
	}
	 /**
	    * 校验数据
	    * @param datas
	    * @return
	    */
	   public LoadResult checkData(List datas) {
			if (datas == null) {
				return LoadResult.error;
			} else if (datas.size() == 0) {
				return LoadResult.empty;
			} else {
				return LoadResult.success;
			}
		}
}
