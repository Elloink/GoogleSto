package com.example.googlesto.fragment;

import java.util.List;

import com.example.googlesto.R;
import com.example.googlesto.adapter.DefaultAdapter;
import com.example.googlesto.domin.SubjectInfo;
import com.example.googlesto.globle.GlobalContants;
import com.example.googlesto.holder.BaseHoder;
import com.example.googlesto.protocol.SubjectProtocol;
import com.example.googlesto.tools.UIUtils;
import com.example.googlesto.view.BaseListView;
import com.example.googlesto.view.LoadingPage.LoadResult;
import com.lidroid.xutils.BitmapUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SubjectFragment extends BaseFragment {
	List<SubjectInfo> datas;

	@Override
	public View createSuccessView() {
		BaseListView listView = new BaseListView(UIUtils.getContext());

		listView.setAdapter(new SubjectAdapter(datas, listView));
		return listView;
	}

	class SubjectAdapter extends DefaultAdapter<SubjectInfo> {

		public SubjectAdapter(List<SubjectInfo> datas, ListView list) {
			super(datas, list);
		}

		@Override
		public BaseHoder<SubjectInfo> getHolder() {
			return new SubjectHolder();
		}

		@Override
		public List<SubjectInfo> onload() {
			SubjectProtocol subjectProtocol = new SubjectProtocol();
			List<SubjectInfo> load = subjectProtocol.load(datas.size());
			datas.addAll(load);
			return load;
		}

		@Override
		public void onInnerItemClick(int position) {
			super.onInnerItemClick(position);

			Toast.makeText(getContext(), datas.get(position).getDes(),
					Toast.LENGTH_SHORT).show();
		}

	}

	class SubjectHolder extends BaseHoder<SubjectInfo> {
		ImageView item_icon;
		TextView item_txt;

		public void refreshView(SubjectInfo datas) {
			this.item_txt.setText(datas.getDes());
			bitmapUtils.display(this.item_icon, GlobalContants.URL
					+ "image?name=" + datas.getUrl());
		}

		@Override
		public View initView() {
			View view = UIUtils.inflate(R.layout.item_subject);
			this.item_icon = (ImageView) view.findViewById(R.id.item_icon);
			this.item_txt = (TextView) view.findViewById(R.id.item_txt);
			return view;
		}

	}

	@Override
	public LoadResult load() {
		SubjectProtocol subjectProtocol = new SubjectProtocol();
		datas = subjectProtocol.load(0);
		return checkData(datas);
	}

}
