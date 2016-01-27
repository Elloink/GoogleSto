package com.example.googlesto.fragment;

import java.util.List;

import com.example.googlesto.adapter.DefaultAdapter;
import com.example.googlesto.domin.CategoryInfo;
import com.example.googlesto.holder.BaseHoder;
import com.example.googlesto.holder.CategoryContentHolder;
import com.example.googlesto.holder.CategoryTitleHolder;
import com.example.googlesto.protocol.CategoryProtocol;
import com.example.googlesto.tools.UIUtils;
import com.example.googlesto.view.BaseListView;
import com.example.googlesto.view.LoadingPage.LoadResult;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class CatagoryFragment extends BaseFragment {
	private List<CategoryInfo> datas;
	public static int ITEM_TITLE = 2;

	@Override
	public View createSuccessView() {
		BaseListView listView = new BaseListView(UIUtils.getContext());
		listView.setAdapter(new CategoryAdapter(datas, listView));

		return listView;
	}

	class CategoryAdapter extends DefaultAdapter<CategoryInfo> {
		private int position;

		public CategoryAdapter(List<CategoryInfo> datas, ListView list) {
			super(datas, list);

		}

		@Override
		public BaseHoder<CategoryInfo> getHolder() {
			if (!datas.get(position).isTitle()) {
				return new CategoryContentHolder();
			}else{
				return new CategoryTitleHolder();
			}
		}

		@Override
		public List<CategoryInfo> onload() {
			return null;
		}

		@Override
		public int getViewTypeCount() {
			return super.getViewTypeCount() + 1;// 又额外增加一种类型
		}

		@Override
		public View getView(int position, View convertview, ViewGroup viewgoup) {
			this.position = position;
			return super.getView(position, convertview, viewgoup);
		}

		@Override
		protected int getDefaultItemViewType(int position) {
			if (datas.get(position).isTitle()) {
				return ITEM_TITLE;
			} else {
				return super.getDefaultItemViewType(position);
			}

		}

		@Override
		public boolean hasMore() {
			return false;// 没有额外数据 false onload方法不会被调用
		}
	}

	@Override
	public LoadResult load() {
		CategoryProtocol protocol = new CategoryProtocol();
		datas = protocol.load(0);
		return checkData(datas);
	}

}
