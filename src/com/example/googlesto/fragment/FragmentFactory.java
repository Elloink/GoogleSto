package com.example.googlesto.fragment;

import java.util.HashMap;
import java.util.Map;

import android.support.v4.app.Fragment;

/**
 * 用于管理fragment，提高fragment的使用次数，不需要每次创建
 * @author threes
 *
 */
public class FragmentFactory extends Fragment {
	//利用map集合重复使用fragment节约内存
	private static Map<Integer, Fragment> mFragments = new HashMap<Integer, Fragment>();

	public static Fragment creatFragment(int position) {
		Fragment fragment = null;  
		fragment = mFragments.get(position);// 在集合中取出fragment
		if (fragment == null) {// 如果在集合中取 不出来，需要重新创建
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
				mFragments.put(position, fragment);// 把创建好的fragment缓存到集合中
			}
		}
		return fragment;
	}
}