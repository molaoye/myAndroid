package com.android;

import java.util.Stack;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

public class ActivityManage {
	private static Stack<Activity> mActivityStack;
	private static ActivityManage mActivityManage;

	private ActivityManage() {
	}

	/**
	 * 单一实例
	 */
	public static ActivityManage getInstance() {
		if (mActivityManage == null) {
			mActivityManage = new ActivityManage();
		}
		return mActivityManage;
	}

	/**
	 * 添加Activity到堆�?
	 */
	public void addActivity(Activity activity) {
		if (mActivityStack == null) {
			mActivityStack = new Stack<Activity>();
		}
		mActivityStack.add(activity);
	}

	/**
	 * 获取栈顶Activity（堆栈中�?���?��压入的）
	 */
	public Activity getTopActivity() {
		Activity activity = mActivityStack.lastElement();
		return activity;
	}

	/**
	 * 结束栈顶Activity（堆栈中�?���?��压入的）
	 */
	public void killTopActivity() {
		Activity activity = mActivityStack.lastElement();
		killActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void killActivity(Activity activity) {
		if (activity != null) {
			mActivityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void killActivity(Class<?> cls) {
		for (Activity activity : mActivityStack) {
			if (activity.getClass().equals(cls)) {
				killActivity(activity);
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void killAllActivity() {
		for (int i = 0, size = mActivityStack.size(); i < size; i++) {
			if (null != mActivityStack.get(i)) {
				mActivityStack.get(i).finish();
			}
		}
		mActivityStack.clear();
	}

	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			killAllActivity();
//			ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
			Log.e("", "", e);
		}
	}
}
