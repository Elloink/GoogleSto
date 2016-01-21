package com.example.googlesto.fragment;

import java.util.Random;

import com.example.googlesto.R;
import com.example.googlesto.view.LoadingPage;
import com.example.googlesto.view.LoadingPage.LoadResult;

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
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (loadingpage == null) {// framelayout���ڿյ�ʱ�򴴽�
			loadingpage = new LoadingPage(getActivity()) {
				@Override
				public LoadResult load() {
					// TODO Auto-generated method stub
					return BaseFragment.this.load();
				}
				@Override
				public View createSuccessView() {
					// TODO Auto-generated method stub
					return  BaseFragment.this.createSuccessView();
				}
			};
		} else {
			com.example.googlesto.tools.ViewUtils.removeParent(loadingpage);// �Ƴ�framelayout֮ǰ�ĵ�
		}
	
//		show();// ���ݷ����������ݣ��л�״̬
		return loadingpage;
	}

	/**
	 * �����ɹ�����
	 * 
	 * @return
	 */
	public abstract View createSuccessView();

	
	/**
	 * ��������
	 * 
	 * @return ���صĽ��
	 */
	public abstract LoadResult load();
public void show(){
	if (loadingpage!=null) {
		loadingpage.show();
	}
}
	
}
