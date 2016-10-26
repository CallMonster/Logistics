package com.zt.hackman.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.WindowManager;

import com.zt.hackman.R;
import com.zt.hackman.base.widget.SystemBarTintManager;

/**
 * Created by Administrator on 2016/9/13.
 */
public class StatusUtils {
    public static void changeStatus(Activity context){
        SystemBarTintManager mTintManager = new SystemBarTintManager(context);
        // 激活状态栏设置
        mTintManager.setStatusBarTintEnabled(true);
        // 激活导航栏设置
        mTintManager.setNavigationBarTintEnabled(true);
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            // Translucent status bar
            context.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    public static void changeTrStatus(Activity context){
        SystemBarTintManager mTintManager = new SystemBarTintManager(context);
        // 激活状态栏设置
        mTintManager.setStatusBarTintEnabled(true);
        // 激活导航栏设置
        mTintManager.setNavigationBarTintEnabled(true);
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            // Translucent status bar
            context.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 自定义颜色
            mTintManager.setStatusBarTintColor(R.color.colorBlack);
            mTintManager.setTintColor(Color.parseColor("#40000000"));
        }
        //mTintManager.setMiuiStatusBarDarkMode(context,false);
    }
}
