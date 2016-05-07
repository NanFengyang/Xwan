package com.example.xwan.Util;

import android.util.Log;

public class LogUtil {
	/**
	 * 日志打印类名
	 * 
	 * @param tag
	 * @param text
	 */
	public static void d(Class<?> javac, String text) {
		if (AppConfigUtil.IS_PRINTLOG && javac != null && text != null) {
			Log.d(javac.getSimpleName(), text);
		}
	}
	/**
	 * 日志打印 Debug 模式
	 * 
	 * @param tag
	 * @param text
	 */
	public static void d(String tag, String text) {
		if (AppConfigUtil.IS_PRINTLOG && tag != null && text != null) {
			Log.d(tag, text);
		}
	}

	/**
	 * 日志打印 error 错误模式
	 * 
	 * @param tag
	 * @param text
	 */
	public static void e(String tag, String text) {
		if (AppConfigUtil.IS_PRINTLOG && tag != null && text != null) {
			Log.e(tag, text);
		}
	}
}
