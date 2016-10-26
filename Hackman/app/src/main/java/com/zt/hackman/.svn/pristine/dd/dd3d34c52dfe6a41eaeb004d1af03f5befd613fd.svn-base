package com.zt.hackman.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.listener.RightListener;
import com.zt.hackman.model.NavModel;
import com.zt.hackman.model.NewsListModel;
import com.zt.hackman.response.HomeResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */
public class NewsListActivity extends BaseActivity implements LeftListener{

    NavModel navModel;
    NewsListModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        initNavBar();
        findViewByIds();
        initView();
    }

    @Override
    protected void initNavBar() {
        navModel = new NavModel(this);
        navModel.setLeftListener(this);
        navModel.setLeftBtn(R.mipmap.main_back);
        navModel.setTitle("更多资讯");
    }

    @Override
    protected void findViewByIds() {
        model = new NewsListModel();
        model.findViewByIds(this);
    }

    @Override
    protected void initView() {
        model.init(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    protected void onDestroy() {
        model.clear();
        model = null;
        super.onDestroy();
    }
}
