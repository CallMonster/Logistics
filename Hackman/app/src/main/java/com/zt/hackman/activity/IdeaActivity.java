package com.zt.hackman.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.listener.RightListener;
import com.zt.hackman.model.IdeaModel;
import com.zt.hackman.model.NavModel;

/**
 * Created by Administrator on 2016/9/22.
 */
public class IdeaActivity extends BaseActivity implements View.OnClickListener,LeftListener,RightListener{
    NavModel navModel;
    IdeaModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);

        initNavBar();
        findViewByIds();
        initView();
        initData();
    }

    @Override
    protected void initNavBar() {
        navModel = new NavModel(this);
        navModel.setLeftListener(this);
        navModel.setRightListener(this);
        navModel.setLeftBtn(R.mipmap.main_back);
        navModel.setTitle("建议反馈");
        //navModel.setRightBtn("联系客服");
    }

    @Override
    protected void findViewByIds() {
        model = new IdeaModel();
        model.findViewByIds(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void leftClick() {
        onBackPressed();
    }

    @Override
    public void rightClick() {

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
			model.commitQuestion();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
