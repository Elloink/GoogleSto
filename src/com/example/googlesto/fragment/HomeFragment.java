package com.example.googlesto.fragment;

import java.util.Random;

import com.example.googlesto.R;
import com.example.googlesto.protocol.HomeProtocol;
import com.example.googlesto.view.LoadingPage.LoadResult;


//import android.R;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

public class HomeFragment extends BaseFragment {
	//����fragment���ص�activity������ʱ�����show��������Ϊ�ռ��ص�ʱ��fragmentû�л��������Բ��ܵ���show()
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		show();
	}
	// �����ɹ�����
	public View createSuccessView() {
		TextView tv = new TextView(getActivity());
		tv.setText("���سɹ���....");
		tv.setTextSize(30);
		return tv;
	}
	
	public LoadResult load() {
		HomeProtocol hProtocol = new HomeProtocol();
		hProtocol.load(0);
		return LoadResult.success;
	}


}
