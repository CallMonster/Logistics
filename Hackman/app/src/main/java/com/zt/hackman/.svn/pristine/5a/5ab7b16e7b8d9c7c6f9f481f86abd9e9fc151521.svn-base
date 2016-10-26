package com.zt.hackman.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.listener.RightListener;
import com.zt.hackman.model.LoginModel;
import com.zt.hackman.model.NavModel;
import com.zt.hackman.utils.StatusUtils;

/**
 * Created by Administrator on 2016/9/13.
 */
public class LoginActivity extends Activity{
    private LoginModel model;
    private int loginState;  //0为正常登陆,1为提示登陆

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatus();
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            loginState = bundle.getInt
                    (Constant.INTNENTS.INTENT_LOGIN_STATE,0);
        }
        setContentView(R.layout.activity_login);
        findViewByIds();
        initNav();
    }
    /**
     * 初始化状态栏
     */
    private void  initStatus(){
        StatusUtils.changeStatus(this);
    }

    private void findViewByIds(){
        model = new LoginModel();
        model.findViewByIds(this,loginState);
    }

    private void initNav(){

    }

    @Override
    protected void onDestroy() {
        model.clear();
        super.onDestroy();
    }
}
