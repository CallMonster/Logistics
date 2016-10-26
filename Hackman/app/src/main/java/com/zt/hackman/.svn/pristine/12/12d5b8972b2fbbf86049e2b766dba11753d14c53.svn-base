package com.zt.hackman.activity;

import android.os.Bundle;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.model.HistoryOrderModel;
import com.zt.hackman.model.NavModel;
import com.zt.hackman.response.MyOrderResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
public class HistoryOrderActivity extends BaseActivity implements LeftListener{
    NavModel navModel;
    HistoryOrderModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);
        initNavBar();
        findViewByIds();
        initData();
        initView();
    }

    @Override
    protected void initNavBar() {
        navModel = new NavModel(this);
        navModel.setLeftListener(this);
        navModel.setLeftBtn(R.mipmap.main_back);
        navModel.setTitle("我的历史订单");
    }

    @Override
    protected void findViewByIds() {
        model = new HistoryOrderModel();
        model.findViewByIds(this);
    }

    @Override
    protected void initView() {
        model.init();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void leftClick() {
        onBackPressed();
    }
}
