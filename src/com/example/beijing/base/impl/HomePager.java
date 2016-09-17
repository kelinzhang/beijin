package com.example.beijing.base.impl;

import com.example.beijing.base.TabBasePager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class HomePager extends TabBasePager {

	public HomePager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initData() {
		tvTitle.setText("�ǻ۱���");
		ibMenu.setVisibility(View.GONE);
		TextView tv = new TextView(mContext);
		tv.setText("��ҳ");
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);
		tv.setGravity(Gravity.CENTER);  //����
		flContent.addView(tv);
	}
}
