package com.example.beijing;

import com.example.beijing.utils.CacheUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

public class WelcomeUI extends Activity implements AnimationListener {
	public static final String IS_OPEN_MAIN_PAGER = "is_open_main_pager";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		View rootView = findViewById(R.id.linear);
		RotateAnimation rorateAnima = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		rorateAnima.setDuration(1000);
		rorateAnima.setFillAfter(true);// 设置动画执行完毕时，停留在完毕的状态下

		ScaleAnimation scaleAnima = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnima.setDuration(1000);
		scaleAnima.setFillAfter(true);// 设置动画执行完毕时，停留在完毕的状态下

		AlphaAnimation alpha = new AlphaAnimation(0, 1);
		alpha.setDuration(1000);
		alpha.setFillAfter(true);// 设置动画执行完毕时，停留在完毕的状态下

		// 把三个动画合在一起，组成一个集合动画
		AnimationSet setAnima = new AnimationSet(false);
		setAnima.addAnimation(rorateAnima);
		setAnima.addAnimation(scaleAnima);
		setAnima.addAnimation(alpha);
		setAnima.setAnimationListener(this);
		rootView.startAnimation(setAnima);
	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub

		// 去文件中取是否打开过程序的值
		boolean isOpenMainPager = CacheUtils.getBoolean(WelcomeUI.this, IS_OPEN_MAIN_PAGER, false);
		Intent intent = new Intent();
		if (isOpenMainPager) {
			// 已经打开过一次主界面，直接进入主界面
			intent.setClass(WelcomeUI.this, MainActivity.class);
		} else {
			// 没有打开过主界面，进入引导页面
			intent.setClass(WelcomeUI.this, GuideActivity.class);

		}
		startActivity(intent);
		// 关闭欢迎界面
		finish();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

}
