package com.zt.hackman.activity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.model.NavModel;

/**
 * 道路交通
 */
public class ClauseActivity extends BaseActivity implements LeftListener{
    NavModel navModel;
    private TextView contentView;
    private RelativeLayout navLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hackman_clause);
        initNavBar();
        findViewByIds();
    }

    @Override
    protected void initNavBar() {
        navModel = new NavModel(this);
        navModel.setLeftListener(this);
        navModel.setLeftBtn(R.mipmap.main_back);
        navModel.setTitle("使用条款");
    }

    @Override
    protected void findViewByIds() {
        contentView =(TextView) findViewById(R.id.hackman_clause_content);
        navLayout = (RelativeLayout) findViewById(R.id.hackman_clause_nav);
        navLayout.setBackgroundResource(R.color.colorWhite);
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
