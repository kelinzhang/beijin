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

	private List<NewsCeterData> leftMenuDataList; // 左侧菜单的数据
	private List<MenuDetailBasePager> pagerList; // 左侧菜单对应的页面

	public NewsCenterPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initData() {
		tvTitle.setText("新闻");
		ibMenu.setVisibility(View.VISIBLE);
		// 把本地缓存的数据取出来
		String json = CacheUtils.getString(mContext, Constants.NEWSCENTER_URL, null);
		if (!TextUtils.isEmpty(json)) {
			processData(json); // 先把旧的新闻显示出来，下面继续使用网络请求最新的数据
		}
		getDataFromNet();

	}

	private void getDataFromNet() {
		// 去服务端抓取数据
		HttpUtils httputils = new HttpUtils();
		httputils.send(HttpMethod.GET, Constants.NEWSCENTER_URL, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				System.out.println("成功" + responseInfo.result);
				// 把数据存储
				CacheUtils.putString(mContext, Constants.NEWSCENTER_URL, responseInfo.result);

				processData(responseInfo.result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {

				System.out.println("失败" + msg);
			}
		});
	}

	// 解析和处理数据
	private void processData(String result) {
		Gson gson = new Gson();
		NewsCenterBean bean = gson.fromJson(result, NewsCenterBean.class);
		// 初始化左侧菜单对应的页面
		pagerList = new ArrayList<MenuDetailBasePager>();
		pagerList.add(new NewsMenuDetailPager(mContext, bean.data.get(0)));
		pagerList.add(new TopicMenuDetailPager(mContext));
		pagerList.add(new PhotosMenuDetailPager(mContext));
		pagerList.add(new InteractMenuDetailPager(mContext));
		// 初始化左侧菜单的数据
		leftMenuDataList = bean.data;
		LeftMenuFragment lfetMenuFragment = ((MainActivity) mContext).getLfetMenuFragment();
		lfetMenuFragment.setMenuListData(leftMenuDataList);

	}

	// 根据位置来切换当前菜单对应的页面
	public void switchCurrentPager(int position) {
		MenuDetailBasePager pager = pagerList.get(position);
		// 把pager中的view对象添加到framelayout中
		View view = pager.getRootView();
		flContent.removeAllViews();
		flContent.addView(view);

		// 标题要跟着去改变
		tvTitle.setText(leftMenuDataList.get(position).title);

		// 初始化当前pager数据
		pager.initData();
	}
}