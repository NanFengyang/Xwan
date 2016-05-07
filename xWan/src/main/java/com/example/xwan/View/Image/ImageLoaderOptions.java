package com.example.xwan.View.Image;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.xwan.R;
import com.example.xwan.Util.FolderUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

public class ImageLoaderOptions {

	/** 图片加载配置 */
	public static DisplayImageOptions getListOptions(int image) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		// 设置图片在下载期间显示的图片
				.showImageOnLoading(image)
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageForEmptyUri(image)
				// // 设置图片加载/解码过程中错误时候显示的图片
				.showImageOnFail(image)
				// 设置下载的图片是否缓存在内存中
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在SD卡中
				.cacheOnDisc(true).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
				.considerExifParams(true).build();
		return options;
	}

	/** 图片加载配置 */
	public static DisplayImageOptions getRoundOptions(int image, int size) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		// 设置图片在下载期间显示的图片
				.showImageOnLoading(image)
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageForEmptyUri(image)
				// // 设置图片加载/解码过程中错误时候显示的图片
				.showImageOnFail(image)
				// 设置下载的图片是否缓存在内存中
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在SD卡中
				.cacheOnDisc(true).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
				.displayer(new RoundedBitmapDisplayer(size))// 是否设置为圆角，弧度为多少
				.considerExifParams(true).build();
		return options;
	}

	/** 图片加载配置 */
	public static DisplayImageOptions getRoundOptions(int size) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		// 设置图片在下载期间显示的图片
				.showImageOnLoading(R.drawable.sh_icon_error_loading)
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageForEmptyUri(R.drawable.sh_icon_error_loading)
				// // 设置图片加载/解码过程中错误时候显示的图片
				.showImageOnFail(R.drawable.sh_icon_error_loading)
				// 设置下载的图片是否缓存在内存中
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在SD卡中
				.cacheOnDisc(true).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
				.displayer(new RoundedBitmapDisplayer(size))// 是否设置为圆角，弧度为多少
				.considerExifParams(true).build();
		return options;
	}

	/** 图片加载配置 */
	public static DisplayImageOptions getListOptions() {

		DisplayImageOptions options = new DisplayImageOptions.Builder()
		// 设置图片在下载期间显示的图片
				.showImageOnLoading(R.drawable.sh_icon_error_loading)
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageForEmptyUri(R.drawable.sh_icon_error_loading)
				// // 设置图片加载/解码过程中错误时候显示的图片
				.showImageOnFail(R.drawable.sh_icon_error_loading)
				// 设置下载的图片是否缓存在内存中
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在SD卡中
				.cacheOnDisc(true).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				// EXACTLY_STRETCHED
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
				// .decodingOptions(android.graphics.BitmapFactory.OptionsdecodingOptions)//设置图片的解码配置
				// .considerExifParams(true)
				// 设置图片下载前的延迟
				// .delayBeforeLoading(1000)// int
				// delayInMillis为你设置的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// .preProcessor(BitmapProcessor preProcessor)
				// .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				// .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
				.displayer(new FadeInBitmapDisplayer(100))// 淡入
				.build();
		return options;
	}

	/**
	 * imageloader 参数设置
	 *
	 * @param context
	 * @return
	 */
	public static ImageLoaderConfiguration getImageConfig(Context context) {
		// 获取到缓存的目录地址
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				FolderUtil.createImageCacheFile());
		// 创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
		@SuppressWarnings("deprecation")
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.memoryCacheExtraOptions(480, 800)
				// 线程池内加载的数量
				.threadPoolSize(3)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				// 默认
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSizePercentage(13)
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024)
				.discCacheSize(10 * 1024 * 1024)
				.memoryCache(new WeakMemoryCache())
				// //将保存的时候的URI名称用MD5 加密
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				// 50 Mb sd卡(本地)缓存的最大值
				.discCacheFileCount(100)
				// 缓存的文件数量
				// 自定义缓存路径
				.diskCache(new UnlimitedDiscCache(cacheDir))
				// .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 10 * 1000))
				.writeDebugLogs() // Remove
									// for
									// release
									// app
				.build();// 开始构建
		return config;
	}
}
