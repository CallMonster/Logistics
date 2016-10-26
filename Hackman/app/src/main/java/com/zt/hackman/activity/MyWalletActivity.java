package com.zt.hackman.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;

import com.zt.hackman.R;
import com.zt.hackman.adapter.TurnOverAdapter;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.base.CyBaseAdapter;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.model.MyWalletModel;
import com.zt.hackman.model.NavModel;
import com.zt.hackman.response.AccountResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/22.
 */
public class MyWalletActivity extends BaseActivity implements LeftListener{
    private NavModel navModel;
    private MyWalletModel model;
    private TurnOverAdapter adpater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywallet);
        initNavBar();
        findViewByIds();
        initView();
        initData();
    }

    @Override
    protected void initNavBar() {
        navModel = new NavModel(this);
        navModel.setLeftBtn(R.mipmap.back);
        navModel.setTitle("我的钱包");
        navModel.titleView.setTextColor(getResources().getColor(R.color.colorWhite));
        navModel.rightBtn.setVisibility(View.GONE);
        navModel.setLeftListener(this);
    }

    @Override
    protected void findViewByIds() {
        model = new MyWalletModel();
        model.findViewByIds(this);
    }

    @Override
    protected void initView(){
        adpater = new TurnOverAdapter(this);
        model.initView(adpater);

        List<AccountResponse.TurnOver> tos = new ArrayList<AccountResponse.TurnOver>();
        tos.add(new AccountResponse.TurnOver());
        tos.add(new AccountResponse.TurnOver());
        tos.add(new AccountResponse.TurnOver());
        tos.add(new AccountResponse.TurnOver());
        tos.add(new AccountResponse.TurnOver());

        adpater.refreshData(tos);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void leftClick() {
        finish();
    }
}
