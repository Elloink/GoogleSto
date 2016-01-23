package com.example.googlesto.holder;

import com.example.googlesto.R;
import com.example.googlesto.adapter.DefaultAdapter;
import com.example.googlesto.tools.UIUtils;

import android.view.View;
import android.widget.RelativeLayout;

public class MoreHoder extends BaseHoder<Integer> {
	public static final int HAS_NO_MORE = 0; // û�ж���������
	public static final int LOAD_ERROR = 1;// ����ʧ��
	public static final int HAS_MORE = 2;// �ж�������

	private RelativeLayout rl_more_loading,rl_more_error;
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.load_more);
		rl_more_loading=(RelativeLayout) view.findViewById(R.id.rl_more_loading);
		rl_more_error=(RelativeLayout) view.findViewById(R.id.rl_more_error);
		return view;
	}

	private DefaultAdapter adapter;

	public MoreHoder(DefaultAdapter adapter) {
		super();
		this.adapter = adapter;

	}

	// ��moreholder���ô˷���ʱ��Ӧ�ü��ظ�������
	@Override
	public View getContentview() {
		loadmore();
		return super.getContentview();
	}

	/**
	 * �����������������һ������ �����ݽ���adapter����
	 */
	private void loadmore() {
		adapter.loadMore();
	}

	/**
	 * ����������������޸�
	 */
	@Override
	public void refreshView(Integer datas) {
		rl_more_error.setVisibility(datas==LOAD_ERROR?View.VISIBLE:View.GONE);
		rl_more_loading.setVisibility(datas==HAS_MORE?View.VISIBLE:View.GONE);
	}

}
