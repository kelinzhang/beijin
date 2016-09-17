package com.example.beijing.base.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.beijing.MainActivity;
import com.example.beijing.base.MenuDetailBasePager;
import com.example.beijing.base.TabBasePager;
import com.example.beijing.base.newscenter.InteractMenuDetailPager;
import com.example.beijing.base.newscenter.NewsMenuDetailPager;
import com.example.beijing.base.newscenter.PhotosMenuDetailPager;
import com.example.beijing.base.newscenter.TopicMenuDetailPager;
import com.example.beijing.domain.NewsCenterBean;
import com.example.beijing.domain.NewsCenterBean.NewsCeterData;
import com.example.beijing.fragment.LeftMenuFragment;
import com.example.beijing.utils.CacheUtils;
import com.example.beijing.utils.Constants;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class NewsCenterPager extends TabBasePager {

	private List<NewsCeterData> leftMenuDataList; // ���˵�������
	private List<MenuDetailBasePager> pagerList; // ���˵���Ӧ��ҳ��

	public NewsCenterPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initData() {
		tvTitle.setText("����");
		ibMenu.setVisibility(View.VISIBLE);
		// �ѱ��ػ��������ȡ����
		String json = CacheUtils.getString(mContext, Constants.NEWSCENTER_URL, null);
		if (!TextUtils.isEmpty(json)) {
			processData(json); // �ȰѾɵ�������ʾ�������������ʹ�������������µ�����
		}
		getDataFromNet();

	}

	private void getDataFromNet() {
		// ȥ�����ץȡ����
		HttpUtils httputils = new HttpUtils();
		httputils.send(HttpMethod.GET, Constants.NEWSCENTER_URL, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				System.out.println("�ɹ�" + responseInfo.result);
				// �����ݴ洢
				CacheUtils.putString(mContext, Constants.NEWSCENTER_URL, responseInfo.result);

				processData(responseInfo.result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {

				System.out.println("ʧ��" + msg);
			}
		});
	}

	// �����ʹ�������
	private void processData(String result) {
		Gson gson = new Gson();
		NewsCenterBean bean = gson.fromJson(result, NewsCenterBean.class);
		// ��ʼ�����˵���Ӧ��ҳ��
		pagerList = new ArrayList<MenuDetailBasePager>();
		pagerList.add(new NewsMenuDetailPager(mContext, bean.data.get(0)));
		pagerList.add(new TopicMenuDetailPager(mContext));
		pagerList.add(new PhotosMenuDetailPager(mContext));
		pagerList.add(new InteractMenuDetailPager(mContext));
		// ��ʼ�����˵�������
		leftMenuDataList = bean.data;
		LeftMenuFragment lfetMenuFragment = ((MainActivity) mContext).getLfetMenuFragment();
		lfetMenuFragment.setMenuListData(leftMenuDataList);

	}

	// ����λ�����л���ǰ�˵���Ӧ��ҳ��
	public void switchCurrentPager(int position) {
		MenuDetailBasePager pager = pagerList.get(position);
		// ��pager�е�view������ӵ�framelayout��
		View view = pager.getRootView();
		flContent.removeAllViews();
		flContent.addView(view);

		// ����Ҫ����ȥ�ı�
		tvTitle.setText(leftMenuDataList.get(position).title);

		// ��ʼ����ǰpager����
		pager.initData();
	}
}