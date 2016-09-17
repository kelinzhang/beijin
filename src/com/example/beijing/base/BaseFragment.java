package com.example.beijing.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

	public Activity mActivity; // 把fragment绑定到那个avtivity上，上下文对象就是那个activity

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = initView(inflater);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initData();
	}

	// 子类必须实现此方法，返回一个view对象，作为当前fragment的布局来展示
	public abstract View initView(LayoutInflater inflater);

	// 如果子类需要初始化自己的数据，把此方法给覆盖
	public void initData() {
		// TODO Auto-generated method stub

	}
}
