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
	private String[] stringArrays;// ��ǩ����
	private FrameLayout fl_menu;// �˵��ĸ�����

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
				Toast.makeText(getApplicationContext(), "�����", 0).show();
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				Toast.makeText(getApplicationContext(), "����ر�", 0).show();
				super.onDrawerOpened(drawerView);
			}
		};
		dl.setDrawerListener(drawerToggle);
		// �ÿ��غ�actionbar������ϵ
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
		// ���ñ�ǩ�»�����ɫ
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
						creatFragment.show();// �������л���ʱ������������������л�״̬
					}
				});
		fl_menu = (FrameLayout) findViewById(R.id.fl_menu);
		MenuHolder mholder = new MenuHolder();
		// ֮ǰ�Ѿ���¼����,�Ͱ���Ϣ���浽���أ�ʵ������ķ������û���Ϣ���õ�����
		// holder.setData(data)
		fl_menu.addView(mholder.getContentview());// ��Ӳ����
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		if (android.os.Build.VERSION.SDK_INT > 11) {
			// ����������ť�ļ���
			SearchView searchView = (SearchView) menu.findItem(
					R.id.action_search).getActionView();
			searchView.setOnQueryTextListener(this);// �����ļ���
		}
		return true;
	}

	// ����viewpager����������Ϊ�����е�viewpagerҪ��fragment���������Բ��ó��õ�pageradapter
	class MainAdapter extends FragmentStatePagerAdapter {
		public MainAdapter(FragmentManager fm) {
			super(fm);
		}

		// ÿ����Ŀ���ص�Fragment
		@Override
		public Fragment getItem(int position) {
			return FragmentFactory.creatFragment(position);
		}

		// һ���м�����Ŀ
		@Override
		public int getCount() {
			return stringArrays.length;
		}

		// ����ÿ����Ŀ�ı���
		@Override
		public CharSequence getPageTitle(int position) {
			return stringArrays[position];
		}

	}

	// ���˵���ѡ�е�ʱ��
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_search) {
			Toast.makeText(getApplicationContext(), "����", Toast.LENGTH_SHORT)
					.show();
		}
		return drawerToggle.onOptionsItemSelected(item)
				| super.onOptionsItemSelected(item);
	}

	// �������ύ��ʱ��
	@Override
	public boolean onQueryTextChange(String query) {
		Toast.makeText(getApplicationContext(), query, 0).show();
		return true;
	}

	// ���������ı������仯
	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}
