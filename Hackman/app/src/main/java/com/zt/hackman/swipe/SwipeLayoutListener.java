package com.zt.hackman.swipe;

import android.view.View;

/**
 * Created by zhengxiaoyong on 16/3/4.
 */
public interface SwipeLayoutListener {

    void onViewFlingPercent(float flingPercent);//回调滑动的百分比

    void onEdgeTouched();//触摸边缘并没有拖动时回调

    void onEdgeDragStarted();//开始拖动时回调

    void onViewReleased(View releasedView);//抬起时回调

    void onViewFlingOver();//达到finish状态时回调
}