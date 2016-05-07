package com.example.xwan.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

import com.example.xwan.View.Image.XImageLoader;

import java.util.LinkedList;
import java.util.List;

public class MApplication extends Application {
	private Context mContext;
	private static MApplication mMApplication;
	private List<Activity> activitys = new LinkedList<Activity>();

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mContext = this;
		mMApplication = this;
		XImageLoader.init(mContext);
	}

	public static MApplication getIntance() {
		return mMApplication;
	}

	public void addActivitys(Activity activity) {
		if (activitys != null) {
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			activitys.add(activity);
		}
	}

	/**
	 * 退出APP
	 */
	public void exit() {
		if (activitys != null && activitys.size() > 0) {
			for (Activity activity : activitys) {
				activity.finish();
			}
		}
		System.exit(0);
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	@SuppressLint("NewApi")
	@Override
	public void onTrimMemory(int level) {
		// TODO Auto-generated method stub
		super.onTrimMemory(level);
	}

}
