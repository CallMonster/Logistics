package com.zt.hackman.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.base.widget.SystemBarTintManager;
import com.zt.hackman.utils.StatusUtils;

/**
 * Created by Administrator on 2016/9/13.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener{

    private TextView loginBtn,registerBtn,watchBtn,goMainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatus();
        setContentView(R.layout.activity_home);
        findViewByIds();
        initView();
    }

    @Override
    protected void initNavBar() {

    }

    @Override
    protected void findViewByIds() {
        loginBtn = (TextView)findViewById(R.id.home_login_btn);
        registerBtn = (TextView)findViewById(R.id.home_register_btn);
        watchBtn = (TextView)findViewById(R.id.home_bottom_text);
        goMainBtn = (TextView)findViewById(R.id.go_main_btn);

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        watchBtn.setOnClickListener(this);
        goMainBtn.setOnClickListener(this);
    }

    /**
     * 初始化状态栏
     */
    private void  initStatus(){
        StatusUtils.changeStatus(this);
    }


    @Override
    protected void initView(){
        watchBtn.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        goMainBtn.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.home_login_btn:
                intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                clear();

                break;
            case R.id.home_register_btn:
                intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                clear();

                break;
            case R.id.home_bottom_text:
                intent = new Intent(this,ClauseActivity.class);
                startActivity(intent);
                break;
            case R.id.go_main_btn:
                intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                clear();
                this.finish();
                break;
        }
    }

    public void clear(){
        loginBtn = null;
        registerBtn=null;
        watchBtn=null;
        goMainBtn=null;
    }
}
