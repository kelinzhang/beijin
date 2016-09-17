package com.example.beijing.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.beijing.MainActivity;
import com.example.beijing.R;
import com.example.beijing.base.BaseFragment;
import com.example.beijing.base.TabBasePager;
import com.example.beijing.base.impl.GovaffirsPager;
import com.example.beijing.base.impl.HomePager;
import com.example.beijing.base.impl.NewsCenterPager;
import com.example.beijing.base.impl.SettingPager;
import com.example.beijing.base.impl.SmartServicePager;
import com.example.beijing.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * @author kelinzhang 主界面 Fragment
 */
public class MainMenuFragment extends BaseFragment implements OnCheckedChangeListener {
	@ViewInject(R.id.vp_content_viewpager)
	private NoScrollViewPager mViewpager;
	@ViewInject(R.id.rg_content_group)
	private RadioGroup mRadioGroup;
	private List<TabBasePager> pagerList;

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.content_fragment, null);
		ViewUtils.inject(this, view); // 把当前view对象注入到xUtils框架中

		return view;
	}

	@Override
	public void initData() {
		pagerList = new ArrayList<TabBasePager>();
		pagerList.add(new HomePager(mActivity));
		pagerList.add(new NewsCenterPager(mActivity));
		pagerList.add(new SmartServicePager(mActivity));
		pagerList.add(new GovaffirsPager(mActivity));
		pagerList.add(new SettingPager(mActivity));

		ContentAdapter mAdapter = new ContentAdapter();
		mViewpager.setAdapter(mAdapter);
		mRadioGroup.check(R.id.rb_content_fragment_home); // 设置默认选中的是home
		pagerList.get(0).initData(); // 初始化首页数据
		((MainActivity) mActivity).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

		mRadioGroup.setOnCheckedChangeListener(this);
	}

	class ContentAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pagerList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TabBasePager pager = pagerList.get(position);
			View rootView = pager.getRootView();
			container.addView(rootView);
			return rootView;
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int index = -1;
		switch (checkedId) {
		case R.id.rb_content_fragment_home:
			index = 0;

			break;

		case R.id.rb_content_fragment_newscenter:
			index = 1;
			break;
		case R.id.rb_content_fragment_smartservice:
			index = 2;
			break;
		case R.id.rb_content_fragment_govaffairs:
			index = 3;
			break;
		case R.id.rb_content_fragment_setting:
			index = 4;
			break;

		}
		if (index != -1) {
			mViewpager.setCurrentItem(index);
			pagerList.get(index).initData();

			if (index == 0 || index == 4) {
				// 菜单不可用
				((MainActivity) mActivity).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			} else {
				((MainActivity) mActivity).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			}
		}
	}

	// 获取新闻中心页面
	public NewsCenterPager getNewsCenterPager() {
		return (NewsCenterPager) pagerList.get(1);
	}
}
