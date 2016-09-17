package com.example.beijing.utils;

import android.content.Context;
import android.content.SharedPreferences;

//�����࣬���е����ݶ��ǲ���SharedPreferenes
public class CacheUtils {

	private static final String CACHE_FILE_NAME = "beijingbeijing";
	private static SharedPreferences sharedPreferences;

	/**
	 * key Ҫȡde���ݵļ� defValue ȱʡֵ
	 */
	public static boolean getBoolean(Context context, String key, boolean defValue) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
		}
		return sharedPreferences.getBoolean(key, defValue);

	}

	/**
	 * */
	public static void putBoolean(Context context, String key, Boolean value) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);

		}
		sharedPreferences.edit().putBoolean(key, value).commit();
	}

	// �洢һ��String���͵�����
	public static void putString(Context context, String key, String value) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);

		}
		sharedPreferences.edit().putString(key, value).commit();
	}

	// ����keyȡ��һ��String���͵�ֵ
	public static String getString(Context context, String key, String defValue) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
		}
		return sharedPreferences.getString(key, defValue);
	}
}
