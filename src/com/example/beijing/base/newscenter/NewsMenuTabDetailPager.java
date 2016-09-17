package com.example.beijing.base.newscenter;

import java.util.List;

import com.example.beijing.R;
import com.example.beijing.base.MenuDetailBasePager;
import com.example.beijing.domain.NewsCenterBean.Children;
import com.example.beijing.domain.TabDetailBean;
import com.example.beijing.domain.TabDetailBean.News;
import com.example.beijing.domain.TabDetailBean.TopNew;
import com.example.beijing.utils.CacheUtils;
import com.example.beijing.utils.Constants;
import com.example.beijing.view.HorizontalScrollViewPager;
import com.example.beijing.view.RefreshListView;
import com.example.beijing.view.RefreshListView.OnRefreshListener;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewsMenuTabDetailPager extends MenuDetailBasePager implements OnPageChangeListener, OnRefreshListener {

	@ViewInject(R.id.hsvp_news_menu_detail_top_news)
	private HorizontalScrollViewPager mViewPager;
	@ViewInject(R.id.tv_news_menu_detail_top_descripe)
	private TextView tvDescription;
	@ViewInject(R.id.ll_news_menu_detail_top_point)
	private LinearLayout llPointGroup;
	@ViewInject(R.id.rlv_news_menu_detail_list_news)
	private RefreshListView mListView;
	private Children mChildren; // ��ǰҳǩ����ҳ�������
	private String url; // ��ǰҳ�����������
	private List<TopNew> topnews; // �����ֲ�ͼ
	private BitmapUtils bitmapUtils; // ͼƬ���ʿ��
	private int previousEnablePosition; // ǰһ��ѡ�е�
	private Internalhandler mHandler;
	private List<News> newsList;
	private HttpUtils httpUtils;
	private TopNewsAdapter topNewsAdapter;
	private NewsAdapter newsAdapter;
	private String more;

	public NewsMenuTabDetailPager(Context context) {
		super(context);

	}

	public NewsMenuTabDetailPager(Context mContext, Children children) {
		super(mContext);
		this.mChildren = children;
		bitmapUtils = new BitmapUtils(mContext);
		bitmapUtils.configDefaultBitmapConfig(Config.ARGB_4444);
	}

	@Override
	public View initView() {
		View view = View.inflate(mContext, R.layout.news_menu_tab_detail, null);
		ViewUtils.inject(this, view); // ��viewע�뵽xutils��
		View topNewsView = View.inflate(mContext, R.layout.news_menu_tab_detail_topnews, null);
		ViewUtils.inject(this, topNewsView); // ��viewע�뵽xutils��
		
		//mListView.addHeaderView(topNewsView);
		mListView.AddCustomHeaderView(topNewsView);
		mListView.setOnRefreshListener(this);
		return view;
	}

	@Override
	public void initData() {
		url = Constants.SERVICE_URL + mChildren.url;
		String json = CacheUtils.getString(mContext, url, null);

		if (!TextUtils.isEmpty(json)) {
			processData(json);
		}
		getDataFromNet();
	}

	// �������ȡ����
	private void getDataFromNet() {
		httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				System.out.println(mChildren.title + "�ɹ�" + responseInfo.result);
				// �����ݴ洢����
				CacheUtils.putString(mContext, url, responseInfo.result);
				processData(responseInfo.result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				System.out.println(mChildren.title + "ʧ��" + msg);
			}
		});
	}
private TabDetailBean parserJSON(String josn){
	Gson gson = new Gson();
	TabDetailBean bean = gson.fromJson(josn, TabDetailBean.class);
	more = bean.data.more;
	if(!TextUtils.isEmpty(more)){
		more=Constants.SERVICE_URL+more;
		
	}
	return bean;
}
	// ������������
	protected void processData(String result) {
		TabDetailBean bean=parserJSON(result);
		topnews = bean.data.topnews;
		if(topNewsAdapter == null){
			topNewsAdapter = new TopNewsAdapter();
			mViewPager.setAdapter(topNewsAdapter);		
			mViewPager.setOnPageChangeListener(this);
		}else{
			topNewsAdapter.notifyDataSetChanged();
		}
	
		// ��ʼ��ͼƬ�������͵�
		llPointGroup.removeAllViews();
		for (int i = 0; i < topnews.size(); i++) {
			View view = new View(mContext);
			view.setBackgroundResource(R.drawable.tab_detail_top_news_point);
			LayoutParams params = new LayoutParams(5, 5);
			if (i != 0) {
				params.leftMargin = 10;
			}
			view.setLayoutParams(params);
			view.setEnabled(false);
			llPointGroup.addView(view);
		}
		previousEnablePosition = 0;
		tvDescription.setText(topnews.get(previousEnablePosition).title);
		llPointGroup.getChildAt(previousEnablePosition).setEnabled(true);

		// ��̬�����ֲ�ͼ�л�����
		/**
		 * 1.ʹ��handlerִ��һ����ʱ���� 2. ������runnable��run�����ᱻִ�У� 3. ʹ��handler����һ����Ϣ 4.
		 * handler���handleMessage�������ܵ���Ϣ ��
		 * 5.��handleMessage�����У���viewPager��ҳ���л�����һ�� 1. ͬʱִ�е�һ�� ʹ��handlerִ��һ����ʱ����
		 */
		if (mHandler == null) {
			mHandler = new Internalhandler();
		}
		mHandler.removeCallbacksAndMessages(null); // �����е���Ϣ���������
		mHandler.postDelayed(new AutoSwitchPagerRunnable(), 4000);
   //��ʼ���б�����
		newsList = bean.data.news;
		if(newsAdapter == null){
			newsAdapter = new NewsAdapter();
			mListView.setAdapter(newsAdapter);
		}else{
			newsAdapter.notifyDataSetChanged();
		}
		
	}

	class NewsAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return newsList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			NewsViewHolder mHolder = null;
			if (convertView == null) {
				convertView = View.inflate(mContext, R.layout.news_menu_tab_detail_news_item, null);
				mHolder = new NewsViewHolder();
				mHolder.ivImage = (ImageView) convertView.findViewById(R.id.iv_news_tab_detail_news_item_image);
				mHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_news_tab_detail_news_item_title);
				mHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_news_tab_detail_news_item_date);
				convertView.setTag(mHolder);
			} else {
				mHolder = (NewsViewHolder) convertView.getTag();
			}

			// ���ؼ���ֵ
			News news = newsList.get(position);
			mHolder.tvTitle.setText(news.title);
			mHolder.tvDate.setText(news.pubdate);
			bitmapUtils.display(mHolder.ivImage, news.listimage);
			return convertView;
		}

	}

	class NewsViewHolder {
		public ImageView ivImage;
		public TextView tvTitle;
		public TextView tvDate;
	}

	class TopNewsAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return topnews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView iv = new ImageView(mContext);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setImageResource(R.drawable.home_scroll_default);
			iv.setOnTouchListener(new TopNewItemTouchListener());
			String topimage = topnews.get(position).topimage;
			// ��һ������ ��ͼƬ��Ҫ��ʾ����һ���ؼ���
			// �ڶ������� ͼƬ��url�ĵ�ַ
			bitmapUtils.display(iv, topimage);
			container.addView(iv);

			return iv;
		}

	}

	class TopNewItemTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mHandler.removeCallbacksAndMessages(null);
				break;

			case MotionEvent.ACTION_UP:
				mHandler.postDelayed(new AutoSwitchPagerRunnable(), 4000);
				break;
			}
			return true;
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		llPointGroup.getChildAt(previousEnablePosition).setEnabled(false);
		llPointGroup.getChildAt(position).setEnabled(true);
		tvDescription.setText(topnews.get(position).title);
		previousEnablePosition = position;
	}

	class Internalhandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			int CurrentItem = mViewPager.getCurrentItem() + 1;
			mViewPager.setCurrentItem(CurrentItem % topnews.size());
			mHandler.postDelayed(new AutoSwitchPagerRunnable(), 4000);
		}

	}

	// �Զ��л�ҳ��������
	class AutoSwitchPagerRunnable implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			mHandler.obtainMessage().sendToTarget();
		}

	}

	@Override
	public void onPullDownRefresh() {
		// TODO Auto-generated method stub
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				mListView.onRefreshFinish();
				CacheUtils.getString(mContext, url, responseInfo.result);
				processData(responseInfo.result);
				Toast.makeText(mContext, "����ˢ�����", 0).show();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				mListView.onRefreshFinish();
			}
		});
	}

	@Override
	public void onLoadingMore() {
		// TODO Auto-generated method stub
		//�鿴�Ƿ��и�������
		if(TextUtils.isEmpty(more)){
			mListView.onRefreshFinish();
			Toast.makeText(mContext, "û�и���������", 0).show();
		}else{
			httpUtils.send(HttpMethod.GET, more, new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					// TODO Auto-generated method stub
					mListView.onRefreshFinish();
					Toast.makeText(mContext, "���ظ������ݳɹ�", 0).show();
            TabDetailBean bean = parserJSON(responseInfo.result);
			newsList.addAll(bean.data.news);
			newsAdapter.notifyDataSetChanged();
			
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					// TODO Auto-generated method stub
					mListView.onRefreshFinish();
					Toast.makeText(mContext, "���ظ�������ʧ��", 0).show();

				}
			});
		}
	}

}
