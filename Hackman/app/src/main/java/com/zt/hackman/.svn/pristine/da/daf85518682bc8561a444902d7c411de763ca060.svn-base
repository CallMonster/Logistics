package com.zt.hackman.activity;

import android.os.Bundle;
import android.view.View;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.model.FriendsModel;
import com.zt.hackman.model.NavModel;

import org.simple.eventbus.EventBus;

/**
 * Created by Administrator on 2016/9/22.
 */
public class InventFriendsActivity extends BaseActivity implements LeftListener{
    NavModel navModel;
    FriendsModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invent_friends);
        initNavBar();
        findViewByIds();
        initView();
        initData();
    }

    @Override
    protected void initNavBar() {
        navModel = new NavModel(this);
        navModel.setLeftBtn(R.mipmap.main_back);
        navModel.setTitle("邀请好友");
        navModel.setLeftListener(this);
        navModel.rightBtn.setVisibility(View.GONE);

    }

    @Override
    protected void findViewByIds() {
        model = new FriendsModel();
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

    @Override
    protected void onDestroy() {
        model = null;
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
