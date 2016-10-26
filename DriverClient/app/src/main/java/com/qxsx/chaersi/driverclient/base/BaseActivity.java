package com.qxsx.chaersi.driverclient.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.qxsx.chaersi.driverclient.R;
import com.qxsx.chaersi.driverclient.utils.OnClickUtils;

/**
 * Created by Chaersi on 16/7/1.
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {

    private ProgressDialog progressDialog;
    public int netState;//0：没有网络   1：WIFI网络   2：WAP网络    3：NET网络
    @Override
    public void onClick(View v) {
        if (OnClickUtils.isFastDoubleClick()) {
            return;
        }
        onClickListener(v);
    }

    public abstract void onClickListener(View v);

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("history","savestate");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getString("savestate");
    }

    private Toast toast;
    public void showTips(String tips) {
        if (toast != null) {
            toast.setText(tips);
            toast.show();
        } else {
            toast = Toast.makeText(getApplicationContext(), tips, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     *
     * @param tips
     * @param gravity
     */
    public void showTips(String tips,int gravity) {
        if (toast != null) {
            toast.setText(tips);
            toast.setGravity(gravity,0,0);
            toast.show();
        } else {
            toast = Toast.makeText(getApplicationContext(), tips, Toast.LENGTH_SHORT);
            toast.setGravity(gravity,0,0);
            toast.show();
        }
    }

    /**
     * 加载进度条
     * @param content
     */
    public void showProgressDialog(String content) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage(content);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 隐藏进度条
     */
    public void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * 隐藏键盘
     */
    public void hideSysInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * 点击EditText意外的View,关闭键盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private AlertDialog.Builder builder;
    public void showAlertDialog(final Activity act, String alertStr){
        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("提示");
        builder.setMessage(alertStr);
        //监听下方button点击事件
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //设置对话框是可取消的
        builder.setCancelable(false);
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}
