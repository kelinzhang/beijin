package com.example.beijing.fragment;

import java.util.List;

import com.example.beijing.MainActivity;
import com.example.beijing.R;
import com.example.beijing.base.BaseFragment;
import com.example.beijing.base.impl.NewsCenterPager;
import com.example.beijing.domain.NewsCenterBean.NewsCeterData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LeftMenuFragment extends BaseFragment implements OnItemClickListener {
	private List<NewsCeterData> mMenulistData;
	private ListView mListView;
	private int selectpostion; // ����itemλ��
	private MenuAdapter mAdapter;

	@Override
	public View initView(LayoutInflater inflater) {
		mListView = new ListView(mActivity);
		mListView.setCacheColorHint(Color.TRANSPARENT);
		mListView.setDividerHeight(0);
		mListView.setPadding(0, 50, 0, 0);
		mListView.setBackgroundColor(Color.BLACK);
		mListView.setSelector(android.R.color.transparent);
		mListView.setOnItemClickListener(this);
		return mListView;
	}

	public void setMenuListData(List<NewsCeterData> menuListData) {
		this.mMenulistData = menuListData;

		selectpostion = 0;
		mAdapter = new MenuAdapter();
		mListView.setAdapter(mAdapter);
		switchNewsCenterContentpager();
	}

	private void switchNewsCenterContentpager() {
		// Ĭ��ѡ�е�0���˵����˵���Ӧ��ҳ����Ҫ�л�Ϊ��0��ҳ��
		MainActivity mainActivity = (MainActivity) mActivity;
		MainMenuFragment fragment = mainActivity.getMainMenuFragment();
		NewsCenterPager newsCenterPager = fragment.getNewsCenterPager();
		newsCenterPager.switchCurrentPager(selectpostion);
	}

	class MenuAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mMenulistData.size();
		}

		@Override
		public Object getItem(int position) {

			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView view = null;
			if (convertView == null) {
				view = (TextView) view.inflate(mActivity, R.layout.menu_item, null);
			} else {
				view = (TextView) convertView;
			}
			view.setText(mMenulistData.get(position).title);

			// ���õ�ǰ��item�Ƿ�ѡ��
			view.setEnabled(position == selectpostion);
			return view;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		selectpostion = position;
		mAdapter.notifyDataSetChanged();

		// �����˵��ջ�ȥ
		SlidingMenu slidingMenu = ((MainActivity) mActivity).getSlidingMenu();
		slidingMenu.toggle();
		// �������м䲿��Ҫ��ʾ��Ӧposition��ҳ��
		switchNewsCenterContentpager();
	}
}
