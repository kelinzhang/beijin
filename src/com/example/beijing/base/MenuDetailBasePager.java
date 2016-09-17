package com.example.beijing.base;

import android.content.Context;
import android.view.View;

/**
 * @author kelinzhang �˵���Ӧ��ҳ��Ļ���
 */
public abstract class MenuDetailBasePager {
	public Context mContext;
	private View rootView;

	public MenuDetailBasePager(Context context) {
		this.mContext = context;
		rootView = initView();
	}

	public abstract View initView();

	public View getRootView() {
		return rootView;

	}

	// ������Ҫ���Ǵ˷�����ʵ���Լ������ݳ�ʼ��
	public void initData() {

	}
}
