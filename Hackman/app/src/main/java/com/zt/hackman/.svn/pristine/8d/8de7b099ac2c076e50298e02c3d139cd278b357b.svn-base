package com.zt.hackman.activity;

import android.os.Bundle;
import android.view.View;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.model.ContractUsModel;
import com.zt.hackman.model.NavModel;
import com.zt.hackman.utils.SystemUtils;

/**
 * Created by Administrator on 2016/9/22.
 */
public class ContractUsActivity extends BaseActivity implements LeftListener,View.OnClickListener{

    NavModel navModel;
    ContractUsModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_us);
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
        navModel.setTitle("联系我们");
    }

    @Override
    protected void findViewByIds() {
        model = new ContractUsModel();
        model.findViewByIds(this,this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.contract_us_call_layout:
                SystemUtils.call("02200001900",this);
                break;
        }
    }
}
