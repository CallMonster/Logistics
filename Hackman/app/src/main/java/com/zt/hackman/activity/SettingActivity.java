package com.zt.hackman.activity;

import android.os.Bundle;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.model.NavModel;
import com.zt.hackman.model.SettingModel;

/**
 * Created by Administrator on 2016/9/22.
 */
public class SettingActivity extends BaseActivity implements LeftListener{
    NavModel navModel;
    SettingModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initNavBar();
        findViewByIds();
        initView();
        initData();
    }

    @Override
    protected void initNavBar() {
        navModel = new NavModel(this);
        navModel.setLeftListener(this);
        navModel.setLeftBtn(R.mipmap.main_back);
        navModel.setTitle("设置");
    }

    @Override
    protected void findViewByIds() {
        model = new SettingModel();
        model.findViewByIds(this);
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
