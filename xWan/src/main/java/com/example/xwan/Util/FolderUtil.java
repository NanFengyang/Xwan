package com.example.xwan.Util;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FolderUtil {

	/**
	 * 将位图保存为文件
	 *
	 * @param bitmap
	 * @param filename
	 * @throws IOException
	 */
	public static boolean saveBitmap(Bitmap bitmap, String filename) throws IOException {
		if (bitmap == null) {
			return false;
		}
		File file = new File(filename);
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 保存网络图片，返回存储路径
	 *
	 * @param bitmap
	 * @param filename
	 * @return
	 */
	public static String saveImgeToshare(Bitmap bitmap) {
		String settext = null;
		String strName = System.currentTimeMillis() + "";
		String filename = FolderUtil.createImageCacheFile() + strName;
		try {
			saveBitmap(bitmap, filename);
			settext = filename;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return settext;
	}

	public static File getSDPath() {
		File sdDir = null;
		if (FolderUtil.isMediaMounted()) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		return sdDir;

	}

	// 创建文件
	public static String createFile() {
		File fileName = new File(getSDPath(), AppConfigUtil.FILE_NAME);
		if (!fileName.exists()) {
			fileName.mkdirs();
		}
		return fileName.toString();
	}

	// 创建用户文件
	public static String createUserFile() {
		File fileUser = new File(createFile(), AppConfigUtil.FILE_USER);
		if (!fileUser.exists()) {
			fileUser.mkdirs();
		}
		return fileUser.toString();
	}

	// 创建缓存文件
	public static String createCacheFile() {
		File fileUser = new File(createFile(), AppConfigUtil.FILE_CACHE);
		if (!fileUser.exists()) {
			fileUser.mkdirs();
		}
		return fileUser.toString();
	}

	// 创建缓存图片加载文件
	public static String createImageCacheFile() {
		File fileUser = new File(createFile(), AppConfigUtil.FILE_IAMGECACHE);
		if (!fileUser.exists()) {
			fileUser.mkdirs();
		}
		return fileUser.toString();
	}

	/**
	 * @Title: isMediaMounted
	 * @Description: sd卡在手机上正常使用状态
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isMediaMounted() {
		// SD卡状态说明
		// Environment.MEDIA_MOUNTED // sd卡在手机上正常使用状态
		// Environment.MEDIA_UNMOUNTED // 用户手工到手机设置中卸载sd卡之后的状态
		// Environment.MEDIA_REMOVED // 用户手动卸载，然后将sd卡从手机取出之后的状态
		// Environment.MEDIA_BAD_REMOVAL // 用户未到手机设置中手动卸载sd卡，直接拨出之后的状态
		// Environment.MEDIA_SHARED // 手机直接连接到电脑作为u盘使用之后的状态
		// Environment.MEDIA_CHECKINGS // 手机正在扫描sd卡过程中的状态
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
}
