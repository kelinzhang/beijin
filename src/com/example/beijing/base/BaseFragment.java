package com.example.beijing.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

	public Activity mActivity; // ��fragment�󶨵��Ǹ�avtivity�ϣ������Ķ�������Ǹ�activity

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

	// �������ʵ�ִ˷���������һ��view������Ϊ��ǰfragment�Ĳ�����չʾ
	public abstract View initView(LayoutInflater inflater);

	// ���������Ҫ��ʼ���Լ������ݣ��Ѵ˷���������
	public void initData() {
		// TODO Auto-generated method stub

	}
}
