package com.learnPro.data;

import android.graphics.Bitmap;
import android.util.Log;

public class FreeBitMap {

	private static int freeBitMapCount = 0;

	public static void freeBitMap(Bitmap bitmap) {
		if (null != bitmap && !bitmap.isRecycled()) {
			bitmap.recycle();
			// freeBitMapCount++;
			// showFreeBitMapCount();
		}
	}

	@SuppressWarnings("unused")
	private static void showFreeBitMapCount() {
		Log.i("free BitMap times", "" + freeBitMapCount);
	}
}
