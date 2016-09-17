package com.example.beijing.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HorizontalScrollViewPager extends ViewPager {

	private int downx;
	private int downy;
	private int movex;
	private int movey;

	public HorizontalScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public HorizontalScrollViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);
			downx = (int) ev.getX();
			downy = (int) ev.getY();
			break;

		case MotionEvent.ACTION_MOVE:
			movex = (int) ev.getX();
			movey = (int) ev.getY();

			int diffx = downx - movex;
			int diffy = downy - movey;
			if (Math.abs(diffx) > Math.abs(diffy)) {
				// 当前页面等于第一个页面，并且是从左向右滑动
				if (getCurrentItem() == 0 && diffx < 0) {
					getParent().requestDisallowInterceptTouchEvent(false);
				} else if (getCurrentItem() == (getAdapter().getCount() - 1) && diffx > 0) {
					// 当前页面等于最后一个页面，并且是从右向左滑动
					getParent().requestDisallowInterceptTouchEvent(false);
				} else {
					getParent().requestDisallowInterceptTouchEvent(true);
				}
			} else { // 竖着滑动，可以拦截
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;

		}

		return super.dispatchTouchEvent(ev);
	}
}
