package com.example.beijing.base.newscenter;

import com.example.beijing.base.MenuDetailBasePager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class PhotosMenuDetailPager extends MenuDetailBasePager {

	public PhotosMenuDetailPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView() {
		TextView tv=new TextView(mContext);
		tv.setText("Ò³Ãæ");
		tv.setTextSize(23);
		return tv;
	}

	

}
