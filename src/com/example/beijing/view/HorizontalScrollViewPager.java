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
				// ��ǰҳ����ڵ�һ��ҳ�棬�����Ǵ������һ���
				if (getCurrentItem() == 0 && diffx < 0) {
					getParent().requestDisallowInterceptTouchEvent(false);
				} else if (getCurrentItem() == (getAdapter().getCount() - 1) && diffx > 0) {
					// ��ǰҳ��������һ��ҳ�棬�����Ǵ������󻬶�
					getParent().requestDisallowInterceptTouchEvent(false);
				} else {
					getParent().requestDisallowInterceptTouchEvent(true);
				}
			} else { // ���Ż�������������
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;

		}

		return super.dispatchTouchEvent(ev);
	}
}
