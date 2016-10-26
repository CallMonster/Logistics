package com.zt.hackman.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import com.zt.hackman.base.BaseApp;


public class DisplayUtil {
	private static Context context = BaseApp.getContext();
	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 * 
	 * @param pxValue
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int px2dip( float pxValue) {
		float scale = getScale();
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 * 
	 * @param dipValue
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int dip2px( float dipValue) {
		float scale = getScale();
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp( float pxValue) {
		float fontScale = getScale();
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px( float spValue) {
		float fontScale = getScale();
		return (int) (spValue * fontScale + 0.5f);
	}

	public static float getScale() {
		DisplayMetrics metric = context.getResources().getDisplayMetrics();
		float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
		return density;
	}

//	public static DisplayBean getDisplayWidthAndHeight(BaseActivity context){
//		DisplayBean bean = new DisplayBean();
//		DisplayMetrics metric = new DisplayMetrics();
//		context.getWindowManager().getDefaultDisplay().getMetrics(metric);
//		int width = metric.widthPixels;     // 屏幕宽度（像素）
//		int height = metric.heightPixels;   // 屏幕高度（像素）
//		bean.width = width;
//		bean.height =height;
//		return bean;
//	}
}
