package com.example.googlesto.fragment;

import java.util.List;

import com.example.googlesto.R;
import com.example.googlesto.domin.SubjectInfo;
import com.example.googlesto.globle.GlobalContants;
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

public class SubjectFragment extends BaseFragment {
	List<SubjectInfo> datas;

	@Override
	public View createSuccessView() {
		BaseListView listView = new BaseListView(UIUtils.getContext());
		listView.setAdapter(new SubjectAdapter());
		return listView;
	}

	class SubjectAdapter extends BaseAdapter {
		
		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public Object getItem(int position) {
			return datas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			ViewHolder holder;
			if(convertView!=null){
				view=convertView;
				holder=(ViewHolder) view.getTag();
			}else{
				view = UIUtils.inflate(R.layout.item_subject);
				holder=new ViewHolder();
				holder.item_icon=(ImageView) view.findViewById(R.id.item_icon);
				holder.item_txt=(TextView) view.findViewById(R.id.item_txt);
				view.setTag(holder);
			}
			SubjectInfo info=datas.get(position);
			holder.item_txt.setText(info.getDes());
			bitmapUtils.display(holder.item_icon, GlobalContants.URL+"image?name="+info.getUrl());
			
			return view;
		}

	}
	class ViewHolder{
		ImageView item_icon;
		TextView item_txt;
	}
	@Override
	public LoadResult load() {
		SubjectProtocol subjectProtocol = new SubjectProtocol();
		datas = subjectProtocol.load(0);
		System.out.println(datas.toString());
		return checkData(datas);
	}

}
