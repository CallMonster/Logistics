package com.zt.hackman.activity;

import android.os.Bundle;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.model.NavModel;
import com.zt.hackman.model.ValidPhoneModel;

/**
 * Created by Administrator on 2016/9/27.
 */
public class ValidPhoneActivity extends BaseActivity implements LeftListener{
    NavModel navModel;

    ValidPhoneModel model;
    //1为系统验证，2为银行卡验证
    private int way = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            way = bundle.getInt(Constant.INTNENTS.INTENT_VALID_PHONE_WAY);
        }

        setContentView(R.layout.activity_valid_phone);
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
        if(way==1){
            navModel.setTitle("修改用户手机号");
        }
        else if(way==2){
            navModel.setTitle("验证手机号");
        }

    }

    @Override
    protected void findViewByIds() {
        model = new ValidPhoneModel();
        model.findViewByIds(this,way);
    }

    @Override
    protected void initView() {
        model.initView("13821578888");
    }

    @Override
    protected void initData() {

    }

    @Override
    public void leftClick() {
        onBackPressed();
    }
}
