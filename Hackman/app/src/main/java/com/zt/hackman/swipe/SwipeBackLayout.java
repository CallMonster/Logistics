package com.zt.hackman.swipe;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.zt.hackman.R;

import java.lang.reflect.Field;

/**
 * Created by zhengxiaoyong on 16/3/4.
 */
public class SwipeBackLayout extends FrameLayout {
    private static final int MIN_FLING_VELOCITY = 50;//dp/s
    private static final int MAX_FLING_VELOCITY = 100;//dp/s
    private static final int DEFAULT_SCRIM_COLOR = 0x99000000;
    private static final int FULL_ALPHA = 255;
    /**
     * 滑动的百分比,当达到这个值则finish当前Activity
     */
    private static final float DEFAULT_SCROLL_RATIO = 0.35f;

    public static final int DEFAULT_FLING_EDGE_SIZE = 18;//dp
    /**
     * Default fling distance
     */
    private int mEdgeFlingDistance;

    private Activity mActivity;

    /**
     * 是否支持右滑
     */
    private boolean mEnable = true;

    private View mContentView;

    private ViewDragHelper mViewDragHelper;

    /**
     * 当前滑动的百分比
     */
    private float mScrollPercent;

    private int mContentViewLeftDx;

    private Drawable mShadowLeft;

    private float mScrimFactor;
    /**
     * 是否正在调用onLayout方法
     */
    private boolean isOnLayout;

    private Rect mRect;

    private SwipeLayoutListener mSwipeLayoutListener;

    private boolean isEdgeTouched = false;

    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mEdgeFlingDistance = ViewConfiguration.get(context).getScaledEdgeSlop();
        mRect = new Rect();
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragCallback());
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        setLeftEdgeShadow(context.getResources().getDrawable(R.mipmap.shadow));
        final float density = getResources().getDisplayMetrics().density;
        final float minVelocity = MIN_FLING_VELOCITY * density;
        mViewDragHelper.setMinVelocity(minVelocity);

        try {
            Field fieldMaxVel = ViewDragHelper.class.getDeclaredField("mMaxVelocity");
            fieldMaxVel.setAccessible(true);
            fieldMaxVel.setInt(mViewDragHelper, (int) (MAX_FLING_VELOCITY * density));
            Field fieldEdgeSize = ViewDragHelper.class.getDeclaredField("mEdgeSize");
            fieldEdgeSize.setAccessible(true);
            fieldEdgeSize.setInt(mViewDragHelper, (int) (DEFAULT_FLING_EDGE_SIZE * density));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setSwipeLayoutListener(SwipeLayoutListener swipeLayoutListener) {
        this.mSwipeLayoutListener = swipeLayoutListener;
    }

    /**
     * Set up contentView which will be moved by user gesture
     *
     * @param view
     */
    private void setSwipeView(View view) {
        mContentView = view;
    }

    public void setEnableGesture(boolean enable) {
        mEnable = enable;
    }


    private void setLeftEdgeShadow(Drawable drawable) {
        mShadowLeft = drawable == null ? new ColorDrawable(Color.TRANSPARENT) : drawable;
        invalidate();
    }

    /**
     * Scroll out contentView and finish the activity
     */
    public void scrollToFinishActivity() {
        final int childWidth = mContentView.getWidth();
        int left = childWidth + mShadowLeft.getIntrinsicWidth() + mEdgeFlingDistance;
        mViewDragHelper.smoothSlideViewTo(mContentView, left, 0);
        invalidate();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!mEnable) {
            return false;
        }
        if (isEdgeTouched) {
            return true;
        }
        final int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mViewDragHelper.cancel();
                return false;
        }
        try {
            return mViewDragHelper.shouldInterceptTouchEvent(event);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mEnable) {
            return false;
        }
        final int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isEdgeTouched = false;
                mViewDragHelper.cancel();
                break;
        }
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        isOnLayout = true;
        if (mContentView != null)
            mContentView.layout(mContentViewLeftDx, 0,
                    mContentViewLeftDx + mContentView.getMeasuredWidth(),
                    mContentView.getMeasuredHeight());
        isOnLayout = false;
    }

    @Override
    public void requestLayout() {
        if (!isOnLayout) {
            super.requestLayout();
        }
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        final boolean drawContent = child == mContentView;
        boolean isDrawChild = super.drawChild(canvas, child, drawingTime);
        if (mScrimFactor > 0 && drawContent
                && mViewDragHelper.getViewDragState() != ViewDragHelper.STATE_IDLE) {
            drawShadow(canvas, child);
            drawScrim(canvas, child);
        }
        return isDrawChild;
    }

    private void drawScrim(Canvas canvas, View child) {
        final int baseAlpha = (DEFAULT_SCRIM_COLOR & 0xff000000) >>> 24;
        final int alpha = (int) (baseAlpha * mScrimFactor);
        final int color = alpha << 24;
        canvas.clipRect(0, 0, child.getLeft(), getHeight());
        canvas.drawColor(color);
    }

    private void drawShadow(Canvas canvas, View child) {
        final Rect childRect = mRect;
        child.getHitRect(childRect);

        mShadowLeft.setBounds(childRect.left - mShadowLeft.getIntrinsicWidth(), childRect.top,
                childRect.left, childRect.bottom);
        mShadowLeft.setAlpha((int) (mScrimFactor * FULL_ALPHA));
        mShadowLeft.draw(canvas);
    }

    public void attachToActivity(Activity activity) {
        if (activity == null) {
            throw new NullPointerException("activity can not be null!");
        }
        mActivity = activity;
        int background = 0;
        TypedArray a = activity.getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowBackground});
        if (a != null) {
            if (a.hasValue(0)) {
                background = a.getResourceId(0, 0);
            }
            a.recycle();
        }
        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(background);
        decor.removeView(decorChild);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(decorChild, params);
        setSwipeView(decorChild);
        decor.addView(this);
    }

    @Override
    public void computeScroll() {
        mScrimFactor = 1 - mScrollPercent;
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private class ViewDragCallback extends ViewDragHelper.Callback {
        private boolean isInvoke = false;

        @Override
        public boolean tryCaptureView(View view, int i) {
            boolean isEdgeLeft = mViewDragHelper.isEdgeTouched(ViewDragHelper.EDGE_LEFT, i);
            boolean directionCheck = false;
            if (isEdgeLeft) {
                directionCheck = mViewDragHelper.checkTouchSlop(ViewDragHelper.DIRECTION_HORIZONTAL, i);
            }
            return isEdgeLeft & directionCheck;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mContentView.getWidth() <= 0 ? 1 : mContentView.getWidth();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return -1;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            mScrollPercent = Math.abs((float) left
                    / (mContentView.getWidth() + mShadowLeft.getIntrinsicWidth()));
            mContentViewLeftDx = left;
            invalidate();
            if (mSwipeLayoutListener != null) {
                mSwipeLayoutListener.onViewFlingPercent(mScrollPercent);
            }

            if (mScrollPercent < DEFAULT_SCROLL_RATIO) {
                isInvoke = false;
            }
            if (mSwipeLayoutListener != null && mScrollPercent >= DEFAULT_SCROLL_RATIO &&
                    mViewDragHelper.getViewDragState() == ViewDragHelper.STATE_DRAGGING
                    && !isInvoke) {
                isInvoke = true;
                mSwipeLayoutListener.onViewFlingOver();
            }

            if (mScrollPercent >= 1) {
                if (mActivity != null && !mActivity.isFinishing()) {
                    mActivity.finish();
                }
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xVel, float yVel) {
            if (mSwipeLayoutListener != null) {
                mSwipeLayoutListener.onViewReleased(releasedChild);
            }
            final int childWidth = releasedChild.getWidth();
            int left = xVel >= 0 && mScrollPercent >= DEFAULT_SCROLL_RATIO ? childWidth
                    + mShadowLeft.getIntrinsicWidth() + mEdgeFlingDistance : 0;
            //判断当前是否达到临界点来决定View的位置
            mViewDragHelper.settleCapturedViewAt(left, 0);
            invalidate();
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return Math.min(Math.max(left, 0), child.getWidth());
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
            if (mSwipeLayoutListener == null)
                return;
            if ((edgeFlags & ViewDragHelper.EDGE_LEFT) != 0 && mViewDragHelper.isPointerDown(pointerId)) {
                mSwipeLayoutListener.onEdgeDragStarted();
            }
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
            isEdgeTouched = true;
            if (mSwipeLayoutListener == null)
                return;
            if ((edgeFlags & ViewDragHelper.EDGE_LEFT) != 0 && mViewDragHelper.isPointerDown(pointerId)) {
                mSwipeLayoutListener.onEdgeTouched();
            }
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }
    }
}
