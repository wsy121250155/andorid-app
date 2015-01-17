package com.learnPro.data;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

public class MyMemoryInfo {
	public static void displayBriefMemory(Activity activity) {
		final ActivityManager activityManager = (ActivityManager) activity
				.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
		activityManager.getMemoryInfo(info);
		Log.i("wsy", "****************************************");
		Log.i("wsy", "系统剩余内存:" + (info.availMem >> 10) + "k");
		Log.i("wsy", "系统是否处于低内存运行：" + info.lowMemory);
		Log.i("wsy", "当系统剩余内存低于" + info.threshold + "时就看成低内存运行");
		Log.i("wsy", "****************************************");
	}
}
