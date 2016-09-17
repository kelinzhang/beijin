package com.example.beijing;

import com.example.beijing.fragment.LeftMenuFragment;
import com.example.beijing.fragment.MainMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

/**
 * alt+shift+j
 * 
 * @author kelinzhang
 *
 */
public class MainActivity extends SlidingFragmentActivity {
	// ���˵�fragment��tag
	private final String LEFT_MENU_FRAGMENT_TAG = "left_menu";

	private final String MAIN_MENU_FRAGMENT_TAG = "main_menu";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.left_menu);// ���˵�����

		SlidingMenu slidingMenu = getSlidingMenu();
		slidingMenu.setMode(SlidingMenu.LEFT); // �������˵�����
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// ������Ļ��������ק���˵�
		slidingMenu.setBehindOffset(200);// ����������������Ļ�ϵĿ��
		initFragment();
	}

	private void initFragment() {
		// ��ȡfragment����������
		FragmentManager fm = getSupportFragmentManager();
		// ��������
		FragmentTransaction ft = fm.beginTransaction();
		// �滻���˵�
		ft.replace(R.id.fl_left_menu, new LeftMenuFragment(), LEFT_MENU_FRAGMENT_TAG);
		ft.replace(R.id.fl_main_context, new MainMenuFragment(), MAIN_MENU_FRAGMENT_TAG);
		// �ύ
		ft.commit();

	}

	// ��ȡ���˵���fragment����
	public LeftMenuFragment getLfetMenuFragment() {
		FragmentManager fm = getSupportFragmentManager();
		LeftMenuFragment leftmunu = (LeftMenuFragment) fm.findFragmentByTag(LEFT_MENU_FRAGMENT_TAG);
		return leftmunu;

	}

	// ��ȡ��ҳ���fragment����
	public MainMenuFragment getMainMenuFragment() {
		FragmentManager fm = getSupportFragmentManager();
		MainMenuFragment mainMenuFragment = (MainMenuFragment) fm.findFragmentByTag(MAIN_MENU_FRAGMENT_TAG);
		return mainMenuFragment;

	}

}
