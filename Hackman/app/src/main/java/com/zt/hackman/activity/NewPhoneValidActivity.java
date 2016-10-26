package com.zt.hackman.activity;

import android.os.Bundle;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.model.NavModel;
import com.zt.hackman.model.NewPhoneValidModel;

/**
 * Created by Administrator on 2016/9/30.
 */
public class NewPhoneValidActivity extends BaseActivity implements LeftListener{

    NavModel navModel;
    NewPhoneValidModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_valid_phone);
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
        navModel.setTitle("新手机号码");
    }

    @Override
    protected void findViewByIds() {
        model = new NewPhoneValidModel();
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
