package com.example.beijing.base.newscenter;

import com.example.beijing.base.MenuDetailBasePager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class TopicMenuDetailPager extends MenuDetailBasePager {

	public TopicMenuDetailPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView() {
		TextView tv=new TextView(mContext);
		tv.setText("–¬Œ≈“≥√Ê");
		tv.setTextSize(23);
		return tv;
	}

	

}
