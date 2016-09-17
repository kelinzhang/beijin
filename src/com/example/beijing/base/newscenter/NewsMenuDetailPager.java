package com.example.beijing.base.newscenter;

import java.util.ArrayList;
import java.util.List;

import com.example.beijing.MainActivity;
import com.example.beijing.R;
import com.example.beijing.base.MenuDetailBasePager;
import com.example.beijing.domain.NewsCenterBean.Children;
import com.example.beijing.domain.NewsCenterBean.NewsCeterData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author kelinzhang 
 * ���Ų˵���Ӧ��ҳ��
 */
public class NewsMenuDetailPager extends MenuDetailBasePager implements OnPageChangeListener {

	@ViewInject(R.id.tpi_news_menu)
	private TabPageIndicator mIndicator;
	//
	// @ViewInject(R.id.ib_news_menu_next_tab)
	// private ImageButton ibNextTab;

	@ViewInject(R.id.vp_news_menu_content)
	private ViewPager mViewPager;

	private List<Children> childrenList; // ��ǰҳ��ҳǩ������
	private List<NewsMenuTabDetailPager> tabPagerList;  //ҳǩ����ҳ��

	public NewsMenuDetailPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public NewsMenuDetailPager(Context context, NewsCeterData newsCeterData) {
		super(context);
		childrenList = newsCeterData.children;
	}

	@Override
	public View initView() {
		View view = View.inflate(mContext, R.layout.news_menu, null);
		ViewUtils.inject(this, view); // ��view����ע�뵽xUtils�����
		return view;
	}

	@Override
	public void initData() {
		tabPagerList = new  ArrayList<NewsMenuTabDetailPager>();
		for (int i = 0; i < childrenList.size(); i++) {
			tabPagerList.add(new NewsMenuTabDetailPager(mContext,childrenList.get(i)));
		}
		
		NewsMenuAdapter mAdapter = new NewsMenuAdapter();
		mViewPager.setAdapter(mAdapter);
		// ��ViewPager�������ø�TabpageIndicator
		// ������֮��ҳǩ�����ݶ�������mViewPager����������NewsMenuAdaper
		mIndicator.setViewPager(mViewPager);
		mIndicator.setOnPageChangeListener(this);
	}

	class NewsMenuAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return childrenList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
		 NewsMenuTabDetailPager pager = tabPagerList.get(position);
		 View rootView=pager.getRootView();
		 container.addView(rootView);
		 //��ʼ������
		 pager.initData();
			return rootView;
		}

		// ���ص��ַ�������ΪTabPagerIndicator��ҳǩ������չʾ
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return childrenList.get(position).title;
		}
	}

	@OnClick(R.id.ib_news_menu_next_tab)
	public void nextTab(View v) {
		// ��һ��ҳǩ��ť�ĵ���¼�
		mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {
//		// ���position����0���˵�����
//		if (position == 0) {
//			// ����������Ļ�����Ի�������
			isEnableSlidingMenu(position == 0);

	}
	//�Ƿ����ò໬�˵�	 
	private void isEnableSlidingMenu(boolean isEnable) {
		if (isEnable) {
			((MainActivity) mContext).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		} else {
			((MainActivity) mContext).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

		}
	}
}
