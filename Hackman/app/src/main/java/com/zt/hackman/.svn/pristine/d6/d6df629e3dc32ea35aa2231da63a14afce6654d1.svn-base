package com.zt.hackman.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.model.ForgetModel;
import com.zt.hackman.model.SetpwdModel;
import com.zt.hackman.utils.StatusUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

/**
 * Created by Administrator on 2016/9/18.
 */
public class SetpwdActivity extends Activity{
    SetpwdModel model;
    String tel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        StatusUtils.changeStatus(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            tel = bundle.getString(Constant.INTNENTS.INTENT_PHONE_NUM);
        }
        setContentView(R.layout.activity_setpwd);
        findViewByIds();
        initNav();
    }

    /**
     * 绑定视图
     */
    private void findViewByIds() {
        model = new SetpwdModel();
        model.findViewByIds(this,tel);
    }

    private void initNav(){
        model.rightBtn.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        model.clear();
        model = null;
        super.onDestroy();
    }
}
