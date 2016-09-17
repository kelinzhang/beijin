package com.example.beijing;

import java.util.ArrayList;
import java.util.List;

import com.example.beijing.utils.CacheUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class GuideActivity extends Activity implements OnPageChangeListener, OnClickListener {

	private ImageView iv;
	private List<ImageView> imageviewList;
	private LinearLayout pointGroup;
	private View view; // 选中点的变量
	private int width;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		ViewPager viewPager = (ViewPager) findViewById(R.id.vp_guide);
		button = (Button) findViewById(R.id.btn_guid_state);
		button.setOnClickListener(this);
		pointGroup = (LinearLayout) findViewById(R.id.ll_guide_point_group);
		view = findViewById(R.id.select_point);

		initData();

		GuideAdapter adapter = new GuideAdapter();
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(this);
		// view绘制流程 ：measure layout draw
		// 监听view控件layout
		// 获得视图树的观察者，监听全部布局的回调
		view.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				// 只执行一次,把当前的事件从试图数的观察者中移除
				view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				// 取出两个点之间的宽度
				width = pointGroup.getChildAt(1).getLeft() - pointGroup.getChildAt(0).getLeft();

			}
		});

	}

	private void initData() {

		int[] imageResIDs = { R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3 };
		imageviewList = new ArrayList<ImageView>();
		View view;
		LayoutParams params;
		for (int i = 0; i < imageResIDs.length; i++) {
			iv = new ImageView(this);
			iv.setBackgroundResource(imageResIDs[i]);
			imageviewList.add(iv);
			// 根据图片的个数，每循环一次向linearLoayout中添加一个点
			view = new View(this);
			view.setBackgroundResource(R.drawable.point_normal);
			params = new LayoutParams(10, 10);
			if (i != 0) {
				params.leftMargin = 30;
			}
			view.setLayoutParams(params);
			pointGroup.addView(view);
		}
	}

	class GuideAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageviewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// 向viewpager中添加一个view对象
			ImageView imageView = imageviewList.get(position);
			container.addView(imageView);
			return imageView;
		}

	}

	@Override
	public void onPageScrollStateChanged(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		// TODO Auto-generated method stub
		int leftMargin = (int) (width * (position + positionOffset));
		RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) view.getLayoutParams();
		params.leftMargin = leftMargin;
		view.setLayoutParams(params);

	}

	// 当页面被选中
	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		if (position == imageviewList.size() - 1) {
			button.setVisibility(View.VISIBLE);

		} else {
			button.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		// 把IS_OPEN_MAIN_PAEGER键，在缓存中存储一个true
		CacheUtils.putBoolean(this, WelcomeUI.IS_OPEN_MAIN_PAGER, true);
		// 打开主页面
		startActivity(new Intent(this, MainActivity.class));
		finish();

	}

}
