package com.zt.hackman.activity;

import android.os.Bundle;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.event.ExitEvent;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.model.AddBankTwoModel;
import com.zt.hackman.model.NavModel;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/26.
 */
public class AddBankTwoActivity extends BaseActivity implements LeftListener{
    NavModel navModel;
    AddBankTwoModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_add_bank_two);
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
        model = new AddBankTwoModel();
        ArrayList<String> banks = new ArrayList<String>();
        banks.add("天津银行");
        banks.add("北京银行");
        banks.add("中国银行");
        banks.add("兴业银行");
        banks.add("交通银行");
        banks.add("广发银行");

        ArrayList<String> cards = new ArrayList<String>();
        cards.add("储蓄卡");
        cards.add("信用卡");

        model.findViewByIds(this,banks,cards);
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
            EventBus.getDefault().post(new ExitEvent(ExitEvent.TYPE_CLOSE));
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
