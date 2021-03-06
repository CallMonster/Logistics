package com.zt.hackman.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zt.hackman.R;
import com.zt.hackman.activity.HomeActivity;
import com.zt.hackman.activity.MainActivity;
import com.zt.hackman.base.widget.MainContentLayout;
import com.zt.hackman.base.widget.SystemBarTintManager;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.listener.RightListener;
import com.zt.hackman.swipe.SwipeBackActivity;
import com.zt.hackman.swipe.SwipeBackLayout;
import com.zt.hackman.utils.StatusUtils;


/**
 * Created by Eugene on 2016/4/21.
 */
public abstract class BaseActivity extends AppCompatActivity{

    private BaseApp baseApp;
    private Context context;
    // 内容区域的布局
    private View contentView;
    public LinearLayout mainLinearLayout,// 主布局
            allliLinearLayout;// 父布局
    public MainContentLayout mainContentLayout;

    // 退出的时间定义
    long lastPressedTime = 0;
    long exitTime = 1000;// 两次下按的时间间隔

    
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParams();
        StatusUtils.changeTrStatus(this);
       // init();
    }
   
    /**
     * 初始参数
     */
    private void initParams(){
        baseApp =BaseApp.getInstance();
        context =this;
        mainContentLayout = new MainContentLayout(this);
        allliLinearLayout = mainContentLayout.getAllLinearLayout();
        //mainProgressActivity = mainContentLayout.getProgressActivity();
        mainLinearLayout = mainContentLayout.getMainLinearLayout();
    }


    public void setBackground(int resId){
        allliLinearLayout.setBackgroundResource(resId);
    }



    /**
     * 通过类名启动Activity
     *
     * @param pClass
     */
    public void startActivity(Class<?> pClass) {
        startActivity(pClass, null);
    }

    public void startActivity(Class<?> pClass, int enterAnim, int exitAnim) {
        startActivity(pClass, null, enterAnim, exitAnim);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     */
    public void startActivity(Class<?> pClass, Bundle pBundle) {
        startActivity(true, pClass, null, pBundle, false, 0, false, 0, 0);
    }

    public void startActivity(Class<?> pClass, Bundle pBundle, int enterAnim,
                              int exitAnim) {
        startActivity(true, pClass, null, pBundle, false, 0, true, enterAnim,
                exitAnim);
    }

    /**
     * 通过Action启动Activity
     *
     * @param pAction
     */
    public void startActivity(String pAction) {
        startActivity(pAction, null);
    }

    public void startActivity(String pAction, int enterAnim, int exitAnim) {
        startActivity(pAction, null, enterAnim, exitAnim);
    }

    /**
     * 通过Action启动Activity，并且含有Bundle数据
     *
     * @param pAction
     * @param pBundle
     */
    public void startActivity(String pAction, Bundle pBundle) {
        startActivity(false, null, pAction, pBundle, false, 0, false, 0, 0);
    }

    public void startActivity(String pAction, Bundle pBundle, int enterAnim,
                              int exitAnim) {
        startActivity(false, null, pAction, pBundle, false, 0, true, enterAnim,
                exitAnim);
    }

    public void startActivityForResult(Class<?> pClass, int requestCode) {
        startActivityForResult(pClass, null, requestCode);
    }

    public void startActivityForResult(Class<?> pClass, int requestCode,
                                       int enterAnim, int exitAnim) {
        startActivityForResult(pClass, null, requestCode, enterAnim, exitAnim);
    }

    public void startActivityForResult(Class<?> pClass, Bundle pBundle,
                                       int requestCode) {
        startActivity(true, pClass, null, pBundle, true, requestCode, false, 0,
                0);
    }

    public void startActivityForResult(Class<?> pClass, Bundle pBundle,
                                       int requestCode, int enterAnim, int exitAnim) {
        startActivity(true, pClass, null, pBundle, true, requestCode, true,
                enterAnim, exitAnim);
    }

    /**
     * 启动 activity
     *
     * @param isClass
     *            ：设置启动方式，是通过 class 还是 action 来启动
     * @param pClass
     *            ：activity class 类
     * @param pAction
     *            ：应用的包名
     * @param pBundle
     *            ：传递的参数
     * @param isBackResult
     *            ：是否返回参数，true：使用 startActivityForResult；false:使用 startActivity
     * @param requestCode
     *            ：isBackResult 为 TRUE 时，设置启动请求参数
     * @param isCustomAnim
     *            ：是否自定义动画
     * @param enterAnim
     *            ：进入动画
     * @param exitAnim
     *            ：退出动画
     */
    public void startActivity(boolean isClass, Class<?> pClass, String pAction,
                              Bundle pBundle, boolean isBackResult, int requestCode,
                              boolean isCustomAnim, int enterAnim, int exitAnim) {
        Intent intent = new Intent();
        if (isClass) {
            intent.setClass(this, pClass);
        } else {
            intent.setAction(pAction);
        }
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }

        if (isBackResult) {
            startActivityForResult(intent, requestCode);
        } else {
            startActivity(intent);
        }

        if (isCustomAnim) {
            overridePendingTransition(enterAnim, exitAnim);
        } else {
            overridePendingTransition(R.anim.push_right_to_left_in_anim,
                    R.anim.no_anim);
        }

    }

    // 退出
    public void exitThis() {
        hideKeyboard();
        onBackPressed();
        overridePendingTransition(R.anim.no_anim,
                R.anim.push_left_to_right_out_anim);
    }


    /**
     * 设置内容区域
     *
     * @param resId
     *            资源文件ID
     */
    @SuppressWarnings("deprecation")
    public void setContentLayout(int resId) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(resId, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.setBackgroundDrawable(null);
        if (null != mainLinearLayout) {
            mainLinearLayout.addView(contentView, layoutParams);
            // 添加Activity到堆栈
            //baseApp.addActivity(this);
        }

        initNavBar();
        findViewByIds();
        initView();
        initData();
    }

    /***
     * 设置内容区域
     *
     * @param view
     *            View对象
     */
    public void setContentLayout(View view) {
        if (null != mainLinearLayout) {
            mainLinearLayout.addView(view);

            initNavBar();
            findViewByIds();
            initView();
            initData();
        }
    }
    /**
     * 初始化导航栏
     */
    protected abstract void initNavBar();

    /**
     * 绑定控件id
     */
    protected abstract void findViewByIds();

    /**
     * 初始化控件，设置监听
     */
    protected abstract void initView();

    /**
     * 请求数据
     */
    protected  abstract void initData();

    /**
     * 打开软键盘
     */
    public void openKeyboard() {
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 100);
    }

    /**
     * 隐藏软键盘
     *
     */
    public void hideKeyboard() {
        try {
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            ((InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(((Activity) context)
                                    .getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            //LogUtils.i("hideInputManager Catch error,skip it!" + e);
            Toast.makeText(this,"关闭软键盘失败",Toast.LENGTH_SHORT).show();
        }

    }
    /**
     * 重写返回键监听处理
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (this instanceof MainActivity||this instanceof HomeActivity) {
                long current = System.currentTimeMillis();
                if (current - lastPressedTime < exitTime) {
                    onBackPressed();
                    return super.onKeyDown(keyCode, event);
                } else {
                    lastPressedTime = current;
                    Toast.makeText(BaseActivity.this, "再按一次退出应用程序", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                exitThis();
                return super.onKeyDown(keyCode, event);
            }

        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onUserLeaveHint() {
        //EventBus.getDefault().post(new HomeBean(HomeBean.TYPE_HOME));
        //super.onUserLeaveHint();
    }

    public boolean isSwipe() {
        boolean isSwipe = true;
        if (this instanceof MainActivity) {
            isSwipe = false;
        }
        return isSwipe;
    }

}
