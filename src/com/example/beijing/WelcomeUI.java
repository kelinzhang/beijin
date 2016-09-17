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
		rorateAnima.setFillAfter(true);// ���ö���ִ�����ʱ��ͣ������ϵ�״̬��

		ScaleAnimation scaleAnima = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnima.setDuration(1000);
		scaleAnima.setFillAfter(true);// ���ö���ִ�����ʱ��ͣ������ϵ�״̬��

		AlphaAnimation alpha = new AlphaAnimation(0, 1);
		alpha.setDuration(1000);
		alpha.setFillAfter(true);// ���ö���ִ�����ʱ��ͣ������ϵ�״̬��

		// ��������������һ�����һ�����϶���
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

		// ȥ�ļ���ȡ�Ƿ�򿪹������ֵ
		boolean isOpenMainPager = CacheUtils.getBoolean(WelcomeUI.this, IS_OPEN_MAIN_PAGER, false);
		Intent intent = new Intent();
		if (isOpenMainPager) {
			// �Ѿ��򿪹�һ�������棬ֱ�ӽ���������
			intent.setClass(WelcomeUI.this, MainActivity.class);
		} else {
			// û�д򿪹������棬��������ҳ��
			intent.setClass(WelcomeUI.this, GuideActivity.class);

		}
		startActivity(intent);
		// �رջ�ӭ����
		finish();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

}
