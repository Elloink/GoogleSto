package com.example.googlesto.fragment;

import java.util.List;

import com.example.googlesto.R;
import com.example.googlesto.protocol.TopProtocol;
import com.example.googlesto.tools.UIUtils;
import com.example.googlesto.view.LoadingPage.LoadResult;

import android.bluetooth.le.ScanCallback;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class TopFragment extends BaseFragment {
	private List<String> data;

	@Override
	public View createSuccessView() {
		ScrollView scrollView = new ScrollView(UIUtils.getContext());
		scrollView.setBackgroundResource(R.drawable.grid_item_bg_normal);
		LinearLayout layout = new LinearLayout(UIUtils.getContext());
		layout.setOrientation(LinearLayout.VERTICAL);
		for (int i = 0; i < data.size(); i++) {
			TextView textView = new TextView(UIUtils.getContext());
			textView.setText(data.get(i));
			layout.addView(textView);
		}
		scrollView.addView(layout);
		return scrollView;
	}

	@Override
	public LoadResult load() {
		TopProtocol protocol = new TopProtocol();
		data = protocol.load(0);
		return checkData(data);
	}

}
