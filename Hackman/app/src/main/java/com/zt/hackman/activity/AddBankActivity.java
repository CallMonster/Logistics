package com.zt.hackman.activity;

import android.os.Bundle;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.event.ExitEvent;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.model.AddBankModel;
import com.zt.hackman.model.NavModel;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

/**
 * Created by Administrator on 2016/9/26.
 */
public class AddBankActivity extends BaseActivity implements LeftListener{
    NavModel navModel;
    AddBankModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_add_bank);
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
        navModel.setTitle("添加银行卡");
    }

    @Override
    protected void findViewByIds() {
        model = new AddBankModel();
        model.findViewByIds(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Subcriber
    public void onEventMain(ExitEvent event){
        if(event.type == ExitEvent.TYPE_CLOSE){
            onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void leftClick() {
        onBackPressed();
    }
}
