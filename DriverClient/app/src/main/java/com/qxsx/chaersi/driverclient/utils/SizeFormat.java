package com.qxsx.chaersi.driverclient.utils;

import android.content.Context;

/**
 * @Description: TODO 尺寸转换工具
 * @author Lee
 * @date 2015/11/18
 */
public class SizeFormat {
	
	/**
	 * dp转换为px
	 * @param context
	 * @param dipValue
	 * @return px
	 */
	public static int diptopx(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * px转换为dp
	 * @param context
	 * @param pxValue
	 * @return dp
	 */
	public static int pxtodip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}
