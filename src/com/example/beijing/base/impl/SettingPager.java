package com.example.beijing.base.impl;

import com.example.beijing.base.TabBasePager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class SettingPager extends TabBasePager {

	public SettingPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
  @Override
public void initData() {
	tvTitle.setText("����");
	ibMenu.setVisibility(View.GONE);
	TextView tv=new TextView(mContext);
	tv.setTextSize(25);
	tv.setText("����");
	tv.setTextColor(Color.RED);
tv.setGravity(Gravity.CENTER);
flContent.addView(tv);
  }
}
