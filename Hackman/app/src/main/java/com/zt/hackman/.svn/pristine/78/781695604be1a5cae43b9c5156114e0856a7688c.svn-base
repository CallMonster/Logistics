package com.zt.hackman.swipe;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

/**
 * Created by zhengxiaoyong on 16/3/4.
 */
public class SwipeBackLayoutHelper {
    private Activity mActivity;
    private SwipeBackLayout mSwipeBackLayout;

    public SwipeBackLayoutHelper(Activity activity) {
        this.mActivity = activity;
        mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mActivity.getWindow().getDecorView().setBackgroundResource(0);
        mSwipeBackLayout = new SwipeBackLayout(mActivity);
    }

    public SwipeBackLayout getSwipeBackLayout() {
        if (mSwipeBackLayout == null) {
            mSwipeBackLayout = new SwipeBackLayout(mActivity);
        }
        return mSwipeBackLayout;
    }
}
