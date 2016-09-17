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
	// 左侧菜单fragment的tag
	private final String LEFT_MENU_FRAGMENT_TAG = "left_menu";

	private final String MAIN_MENU_FRAGMENT_TAG = "main_menu";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.left_menu);// 左侧菜单布局

		SlidingMenu slidingMenu = getSlidingMenu();
		slidingMenu.setMode(SlidingMenu.LEFT); // 设置左侧菜单可用
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 整个屏幕都可以拖拽出菜单
		slidingMenu.setBehindOffset(200);// 设置主界面留在屏幕上的宽度
		initFragment();
	}

	private void initFragment() {
		// 获取fragment管理器对象
		FragmentManager fm = getSupportFragmentManager();
		// 开启事务
		FragmentTransaction ft = fm.beginTransaction();
		// 替换左侧菜单
		ft.replace(R.id.fl_left_menu, new LeftMenuFragment(), LEFT_MENU_FRAGMENT_TAG);
		ft.replace(R.id.fl_main_context, new MainMenuFragment(), MAIN_MENU_FRAGMENT_TAG);
		// 提交
		ft.commit();

	}

	// 获取左侧菜单的fragment对象
	public LeftMenuFragment getLfetMenuFragment() {
		FragmentManager fm = getSupportFragmentManager();
		LeftMenuFragment leftmunu = (LeftMenuFragment) fm.findFragmentByTag(LEFT_MENU_FRAGMENT_TAG);
		return leftmunu;

	}

	// 获取主页面的fragment对象
	public MainMenuFragment getMainMenuFragment() {
		FragmentManager fm = getSupportFragmentManager();
		MainMenuFragment mainMenuFragment = (MainMenuFragment) fm.findFragmentByTag(MAIN_MENU_FRAGMENT_TAG);
		return mainMenuFragment;

	}

}
