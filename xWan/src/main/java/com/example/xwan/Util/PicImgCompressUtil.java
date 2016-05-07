package com.example.xwan.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 相机拍照的图片压缩类。
 */
public class PicImgCompressUtil {

	private static String TAG = "PicImgCompress";

	/**
	 * 计算图片的缩放值
	 *
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return int
	 */
	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
			int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		Log.d(TAG, "befor-width:" + width);
		Log.d(TAG, "befor-height:" + height);
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		Log.d(TAG, "inSampleSize" + inSampleSize);
		if (inSampleSize <= 0) {
			inSampleSize = 1;
		}
		return inSampleSize;
	}

	/**
	 * 根据路径获得图片并压缩，返回bitmap数组用于显示
	 *
	 * @param b
	 * @return byte[]
	 */
	public static byte[] getSmallBitmapArray(byte[] b) {
		ByteArrayInputStream isBm = new ByteArrayInputStream(b);
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(isBm, null, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		isBm = new ByteArrayInputStream(b);
		Bitmap bm = BitmapFactory.decodeStream(isBm, null, options);
		Log.d(TAG, "bm:" + bm.toString());
		return Bitmap2Bytes(bm);
	}

	/**
	 * 根据图片字节数组获得图片并压缩，返回bitmap用于显示
	 *
	 * @param b
	 * @return Bitmap
	 */
	public static Bitmap getSmallBitmap(byte[] b) {
		ByteArrayInputStream isBm = new ByteArrayInputStream(b);
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(isBm, null, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		isBm = new ByteArrayInputStream(b);
		Bitmap bm = BitmapFactory.decodeStream(isBm, null, options);
		Log.d(TAG, "bm:" + bm.toString());
		return bm;
	}

	/**
	 * 根据路径获得图片并压缩，返回480bitmap用于显示
	 *
	 * @param path
	 * @return Bitmap
	 */
	public static Bitmap get480Bitmap(String path) {
		Log.d(TAG, "path:" + path);
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		Bitmap bm=null;
		try{
		bm= BitmapFactory.decodeFile(path, options);
		}catch (OutOfMemoryError error){
			return get100Bitmap(path);
		}
		return bm;
	}

	/**
	 * 根据路径获得图片并压缩，返回bitmap用于显示
	 *
	 * @param path
	 * @return Bitmap
	 */
	public static Bitmap get100Bitmap(String path) {
		Log.d(TAG, "path:" + path);
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 100, 600);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		Bitmap bm =null;
		try{
			bm=BitmapFactory.decodeFile(path, options);
		} catch (OutOfMemoryError error){
			error.printStackTrace();
			return getBitmap(path,60,60);
		}

		return bm;
	}
	/**
	 * 根据路径获得图片并压缩，返回bitmap用于显示
	 *
	 * @param path
	 * @return Bitmap
	 */
	public static Bitmap getBitmap(String path,int w,int h) {
		Log.d(TAG, "path:" + path);
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, w, h);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		Bitmap bm =null;
		try{
			bm=BitmapFactory.decodeFile(path, options);
		} catch (OutOfMemoryError error){
			error.printStackTrace();
		}

		return bm;
	}
	/**
	 * 把bitmap 转换为数组字节对象
	 *
	 * @param bm
	 * @return byte[]
	 */
	private static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 50, baos);
		return baos.toByteArray();
	}
}
