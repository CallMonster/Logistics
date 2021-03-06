package com.zt.hackman.activity;

import android.os.Bundle;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.model.DepositModel;
import com.zt.hackman.model.NavModel;
import com.zt.hackman.response.DepositResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */
public class DepositActivity extends BaseActivity implements LeftListener{
    NavModel navModel;
    DepositModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
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
        navModel.setTitle("提现");
    }

    @Override
    protected void findViewByIds() {
        model = new DepositModel();
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
