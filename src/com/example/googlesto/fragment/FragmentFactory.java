package com.example.googlesto.fragment;

import java.util.HashMap;
import java.util.Map;

import android.support.v4.app.Fragment;

/**
 * ���ڹ���fragment�����fragment��ʹ�ô���������Ҫÿ�δ���
 * @author threes
 *
 */
public class FragmentFactory extends Fragment {
	//����map�����ظ�ʹ��fragment��Լ�ڴ�
	private static Map<Integer, Fragment> mFragments = new HashMap<Integer, Fragment>();

	public static Fragment creatFragment(int position) {
		Fragment fragment = null;  
		fragment = mFragments.get(position);// �ڼ�����ȡ��fragment
		if (fragment == null) {// ����ڼ�����ȡ ����������Ҫ���´���
			if (0 == position) {
				fragment = new HomeFragment();
			} else if (1 == position) {
				fragment = new AppFragment();
			} else if (2 == position) {
				fragment = new GameFragment();
			} else if (3 == position) {
				fragment = new SubjectFragment();
			} else if (4 == position) {
				fragment = new CatagoryFragment();
			} else if (5 == position) {
				fragment = new TopFragment();
			}
			if (fragment != null) {
				mFragments.put(position, fragment);// �Ѵ����õ�fragment���浽������
			}
		}
		return fragment;
	}
}