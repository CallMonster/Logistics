package com.zt.hackman.activity;

import android.os.Bundle;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.listener.RightListener;
import com.zt.hackman.model.ApproveStateModel;
import com.zt.hackman.model.NavModel;

/**
 * Created by Administrator on 2016/9/28.
 */
public class ApproveStateActivity extends BaseActivity implements LeftListener,RightListener{
    NavModel navModel;
    ApproveStateModel model;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle=getIntent().getExtras();
        setContentView(R.layout.activity_approve_state);
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
        navModel.setTitle("认证中");
        navModel.setRightBtn("重新认证");
        navModel.setRightListener(this);
    }

    @Override
    protected void findViewByIds() {
        model = new ApproveStateModel();
        model.findViewByIds(this);
    }

    @Override
    protected void initView() {
        if(bundle!=null) {
            model.initView(bundle.getInt("certification_state",-2));
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void leftClick() {
        onBackPressed();
    }

    @Override
    public void rightClick() {
        finish();
        startActivity(EditHackmanOneActivity.class);
    }
}
