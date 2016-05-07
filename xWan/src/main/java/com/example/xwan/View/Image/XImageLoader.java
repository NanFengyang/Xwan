package com.example.xwan.View.Image;

import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;

public class XImageLoader {
	/**
	 * 图片加载器初始化
	 *
	 * @param context
	 */
	public static void init(Context context) {
		ImageLoader mImageLoader = ImageLoader.getInstance();
		mImageLoader.init(ImageLoaderOptions.getImageConfig(context));
	}

	/**
	 * 加载图片
	 *
	 * @return
	 */
	public static ImageLoader getImageLoader() {
		return ImageLoader.getInstance();
	}
}
