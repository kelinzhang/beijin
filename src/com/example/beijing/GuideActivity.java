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
	private View view; // ѡ�е�ı���
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
		// view�������� ��measure layout draw
		// ����view�ؼ�layout
		// �����ͼ���Ĺ۲��ߣ�����ȫ�����ֵĻص�
		view.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				// ִֻ��һ��,�ѵ�ǰ���¼�����ͼ���Ĺ۲������Ƴ�
				view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				// ȡ��������֮��Ŀ��
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
			// ����ͼƬ�ĸ�����ÿѭ��һ����linearLoayout�����һ����
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
			// ��viewpager�����һ��view����
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

	// ��ҳ�汻ѡ��
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
		// ��IS_OPEN_MAIN_PAEGER�����ڻ����д洢һ��true
		CacheUtils.putBoolean(this, WelcomeUI.IS_OPEN_MAIN_PAGER, true);
		// ����ҳ��
		startActivity(new Intent(this, MainActivity.class));
		finish();

	}

}
