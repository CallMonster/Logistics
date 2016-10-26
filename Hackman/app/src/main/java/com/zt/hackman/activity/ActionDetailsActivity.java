package com.zt.hackman.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.model.ActionDetailsModel;
import com.zt.hackman.model.NavModel;
import com.zt.hackman.utils.StringUtils;

/**
 * Created by Administrator on 2016/9/28.
 */
public class ActionDetailsActivity extends BaseActivity implements LeftListener{
    NavModel navModel;
    ActionDetailsModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_detials);
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
        navModel.setTitle("活动详情");
    }

    @Override
    protected void findViewByIds() {
        model = new ActionDetailsModel();
        model.findViewByIds(this);
    }

    @Override
    protected void initView() {
        Bundle bundle =  getIntent().getExtras();
        if(bundle==null||bundle.getString(Constant.INTNENTS.INTENT_ACTION_ID)==null||
                StringUtils.isEmpty(bundle.getString(Constant.INTNENTS.INTENT_ACTION_ID))){
            Toast.makeText(this,"该资讯内容暂时无法了解",Toast.LENGTH_SHORT).show();
        }else {
            model.initView(bundle.getString(Constant.INTNENTS.INTENT_ACTION_ID));
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void leftClick() {
        onBackPressed();
    }
}
