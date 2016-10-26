package com.zt.hackman.activity;

import android.os.Bundle;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.model.NavModel;

/**
 * 关于我们
 */
public class AboutActivity extends BaseActivity implements LeftListener{
    NavModel navModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initNavBar();
    }

    @Override
    protected void initNavBar() {
        navModel = new NavModel(this);
        navModel.setLeftListener(this);
        navModel.setLeftBtn(R.mipmap.main_back);
        navModel.setTitle("关于我们");
    }

    @Override
    protected void findViewByIds() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void leftClick() {
        onBackPressed();
    }
}
