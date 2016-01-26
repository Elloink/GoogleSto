package com.example.googlesto;

import com.example.googlesto.fragment.AppFragment;
import com.example.googlesto.fragment.BaseFragment;
import com.example.googlesto.fragment.CatagoryFragment;
import com.example.googlesto.fragment.FragmentFactory;
import com.example.googlesto.fragment.FragmentFactory;
import com.example.googlesto.fragment.GameFragment;
import com.example.googlesto.fragment.HomeFragment;
import com.example.googlesto.fragment.SubjectFragment;
import com.example.googlesto.fragment.TopFragment;
import com.example.googlesto.holder.MenuHolder;
import com.example.googlesto.tools.UIUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements OnQueryTextListener {
	private DrawerLayout dl;
	private ViewPager myViewPager;
	private ActionBarDrawerToggle drawerToggle;
	private ActionBar actionBar;
	private PagerTabStrip pagertabstrip;
	private String[] stringArrays;// 标签名字
	private FrameLayout fl_menu;// 菜单的根布局

	@Override
	protected void init() {
		super.init();
		stringArrays = UIUtils.getStringArray(R.array.tab_names);
	}

	@Override
	protected void initActionBar() {
		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		drawerToggle = new ActionBarDrawerToggle(this, dl,
				R.drawable.ic_drawer_am, R.string.drawer_open) {
			@Override
			public void onDrawerClosed(View drawerView) {
				Toast.makeText(getApplicationContext(), "抽屉打开", 0).show();
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				Toast.makeText(getApplicationContext(), "抽屉关闭", 0).show();
				super.onDrawerOpened(drawerView);
			}
		};
		dl.setDrawerListener(drawerToggle);
		// 让开关和actionbar建立联系
		drawerToggle.syncState();
		super.initActionBar();
	}

	@Override
	protected void initView() {
		super.initView();
		setContentView(R.layout.activity_main);
		dl = (DrawerLayout) findViewById(R.id.dl);
		myViewPager = (ViewPager) findViewById(R.id.vp);
		pagertabstrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
		// 设置标签下划线颜色
		pagertabstrip.setTabIndicatorColor(getResources().getColor(
				R.color.indicatorcolor));
		myViewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
		myViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						super.onPageSelected(position);
						BaseFragment creatFragment = FragmentFactory
								.creatFragment(position);
						creatFragment.show();// 当界面切换的时候重新请求服务器，切换状态
					}
				});
		fl_menu = (FrameLayout) findViewById(R.id.fl_menu);
		MenuHolder mholder = new MenuHolder();
		// 之前已经登录过了,就把信息保存到本地，实现下面的方法把用户信息设置到界面
		// holder.setData(data)
		fl_menu.addView(mholder.getContentview());// 添加侧边栏
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		if (android.os.Build.VERSION.SDK_INT > 11) {
			// 设置搜索按钮的监听
			SearchView searchView = (SearchView) menu.findItem(
					R.id.action_search).getActionView();
			searchView.setOnQueryTextListener(this);// 搜索的监听
		}
		return true;
	}

	// 创建viewpager适配器，因为此例中的viewpager要和fragment关联，所以不用常用的pageradapter
	class MainAdapter extends FragmentStatePagerAdapter {
		public MainAdapter(FragmentManager fm) {
			super(fm);
		}

		// 每个条目返回的Fragment
		@Override
		public Fragment getItem(int position) {
			return FragmentFactory.creatFragment(position);
		}

		// 一共有几个条目
		@Override
		public int getCount() {
			return stringArrays.length;
		}

		// 返回每个条目的标题
		@Override
		public CharSequence getPageTitle(int position) {
			return stringArrays[position];
		}

	}

	// 当菜单栏选中的时候
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_search) {
			Toast.makeText(getApplicationContext(), "搜索", Toast.LENGTH_SHORT)
					.show();
		}
		return drawerToggle.onOptionsItemSelected(item)
				| super.onOptionsItemSelected(item);
	}

	// 当搜索提交的时候
	@Override
	public boolean onQueryTextChange(String query) {
		Toast.makeText(getApplicationContext(), query, 0).show();
		return true;
	}

	// 当搜索的文本发生变化
	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}
