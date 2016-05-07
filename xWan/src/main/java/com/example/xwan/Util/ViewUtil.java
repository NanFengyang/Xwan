package com.example.xwan.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.ScrollView;

public class ViewUtil {
	private static String TAG = "ViewUtil";

	/**
	 * 记录宽高
	 *
	 * @author nanfeng
	 *
	 */
	public static class Size {
		public int width = 0;
		public int height = 0;
	}

	/**
	 * 返回控件的宽度和高度
	 *
	 * @param view
	 * @return Size
	 */
	public static Size getViewSize(View view) {
		Size size = new Size();
		int width = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int height = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		view.measure(width, height);
		size.width = view.getMeasuredWidth();
		size.height = view.getMeasuredHeight();
		return size;
	}

	/**
	 * 把一个View转化成图片
	 *
	 * @param v
	 * @return
	 */
	public static Bitmap getViewBitmap(View v) {
		v.clearFocus();
		v.setPressed(false);

		boolean willNotCache = v.willNotCacheDrawing();
		v.setWillNotCacheDrawing(false);

		// Reset the drawing cache background color to fully transparent
		// for the duration of this operation
		int color = v.getDrawingCacheBackgroundColor();
		v.setDrawingCacheBackgroundColor(0);

		if (color != 0) {
			v.destroyDrawingCache();
		}
		v.buildDrawingCache();
		Bitmap cacheBitmap = v.getDrawingCache();
		if (cacheBitmap == null) {
			LogUtil.d(TAG, "failed getViewBitmap(" + v + ")");
			return null;
		}

		Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
		// Restore the view
		v.destroyDrawingCache();
		v.setWillNotCacheDrawing(willNotCache);
		v.setDrawingCacheBackgroundColor(color);

		return bitmap;
	}

	/**
	 * 把scrollview转化为图片
	 * **/
	public static Bitmap getBitmapByScrollView(ScrollView scrollView, int w) {
		int h = 0;
		Bitmap bitmap = null;
		// 获取scrollView实际高度
		for (int i = 0; i < scrollView.getChildCount(); i++) {
			h += scrollView.getChildAt(i).getHeight();
		}
		LogUtil.d(TAG, "实际高度:" + h);
		LogUtil.d(TAG, " 高度:" + scrollView.getHeight());
		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
				Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(bitmap);
		scrollView.draw(canvas);
		return bitmap;
	}

	/**
	 * 把listview转化为图片
	 * **/
	public static Bitmap getBitmapByListView(ListView listview, int w) {
		int h = 0;
		Bitmap bitmap = null;
		// 获取scrollView实际高度
		for (int i = 0; i < listview.getChildCount(); i++) {
			h += listview.getChildAt(i).getHeight();
		}
		LogUtil.d(TAG, "实际高度:" + h);
		LogUtil.d(TAG, " 高度:" + listview.getHeight());
		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(bitmap);
		listview.draw(canvas);
		return bitmap;
	}

	/** 获取屏幕的宽度 */
	public final static Size getWindowsSize(Context mContext) {
		WindowManager manager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		Size size = new Size();
		size.width = manager.getDefaultDisplay().getWidth();
		size.height = manager.getDefaultDisplay().getHeight();
		return size;
	}
}
