package com.example.beijing.utils;

import android.content.Context;
import android.content.SharedPreferences;

//缓存类，所有的数据都是采用SharedPreferenes
public class CacheUtils {

	private static final String CACHE_FILE_NAME = "beijingbeijing";
	private static SharedPreferences sharedPreferences;

	/**
	 * key 要取de数据的键 defValue 缺省值
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

	// 存储一个String类型的数据
	public static void putString(Context context, String key, String value) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);

		}
		sharedPreferences.edit().putString(key, value).commit();
	}

	// 根据key取出一个String类型的值
	public static String getString(Context context, String key, String defValue) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
		}
		return sharedPreferences.getString(key, defValue);
	}
}
